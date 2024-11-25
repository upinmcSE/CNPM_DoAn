import pandas as pd
import numpy as np
import requests
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from flask import Flask, request, jsonify

api_url = "http://localhost:8081/coffee/api/v1/orders/order-orderline"

# Khởi tạo ứng dụng Flask
app = Flask(__name__)

def get_recommendations(customer_id, df, num_recommendations=3):
    # Lọc các sản phẩm mà khách hàng đã mua
    purchased_products = df[df['customerId'] == customer_id]

    print("Sản phẩm đã mua: ",purchased_products.head())

    # Tạo ma trận TF-IDF cho các sản phẩm, với trọng số từ 'amount'
    tfidf = TfidfVectorizer()

    # Tạo một bản sao độc lập của DataFrame
    df = df.copy()

    df.loc[:, 'product_description'] = df.apply(
    lambda row: str(row['productId']) + ' ' + ' '.join([str(row['amount'])] * row['amount']), axis=1)
    
    tfidf_matrix = tfidf.fit_transform(df['product_description'])

    print("ma trận: ",tfidf_matrix)
    
    # Tính toán độ tương đồng cosine
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    print("Độ tương đồng",cosine_sim)
    
    # Lấy gợi ý từ các sản phẩm đã mua
    recommendations = set()

    for _, row in purchased_products.iterrows():
        product_id = row['productId']
        
        # Tìm chỉ số của sản phẩm trong ma trận TF-IDF
        idx = df[df['productId'] == product_id].index[0]
        
        # Tính độ tương đồng với các sản phẩm khác
        sim_scores = list(enumerate(cosine_sim[idx]))
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
        
        # Thêm sản phẩm vào danh sách gợi ý (trừ sản phẩm đã mua)
        for i in sim_scores[1:]:
            similar_product_id = df.iloc[i[0]]['productId']
            if similar_product_id not in purchased_products['productId'].values:
                print("hhihi",similar_product_id)
                recommendations.add(similar_product_id)
            
            if len(recommendations) >= num_recommendations:
                break
    
    return list(recommendations)


# Tạo endpoint cho API
@app.route('/recommendations', methods=['GET'])
def recommendations():
    customer_id = request.args.get('customerId')
    token = request.args.get('token')
    headers = {
        "Authorization": f"Bearer {token}"
    }
    if customer_id is None:
        return jsonify({'error': 'customerId is required'}), 400
    
    # Lấy dữ liệu từ API
    response = requests.get(api_url, headers=headers)

    if response.status_code == 200:
        data = response.json()

        # Chuyển đổi dữ liệu thành DataFrame
        df = pd.DataFrame(data)
        
        # Chỉ giữ lại ba cột: customerId, productId, amount
        df_filtered = df[['customerId', 'productId', 'amount']]
        
        print(df_filtered.head())
        # Lấy gợi ý sản phẩm cho customerId
        product_recommendations = get_recommendations(customer_id, df_filtered)
        
        return jsonify({'customerId': customer_id, 'recommendations': product_recommendations})
    else:
        return jsonify({'error': 'Failed to retrieve data from the backend API'}), 500


# Chạy ứng dụng Flask
if __name__ == '__main__':
    app.run(debug=True, port=5000)  # Chạy trên cổng 5000
