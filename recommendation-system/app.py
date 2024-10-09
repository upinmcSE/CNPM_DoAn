import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
import random
from flask import Flask, request, jsonify

# Bước 1: Tạo dữ liệu giả
random.seed(42)  # Để tạo dữ liệu có thể tái tạo
customers = [f'{i:05d}' for i in range(1, 101)]  # 100 khách hàng
products = [f'{i * 1000}' for i in range(1, 21)]  # 20 sản phẩm: 1000, 2000, ..., 20000

data = {
    'customers': [],
    'products': [],
    'amount': [],
    'date': []
}
for _ in range(500):
    customer_id = random.choice(customers)
    product_id = random.choice(products)
    amount = random.randint(1, 5)  # Số lượng ngẫu nhiên từ 1 đến 5
    date = f"2024-09-{random.randint(10, 30):02d}"  # Ngày ngẫu nhiên trong tháng 09/2024
    
    data['customers'].append(customer_id)
    data['products'].append(product_id)
    data['amount'].append(amount)
    data['date'].append(date)

# Tạo DataFrame từ dữ liệu giả
df = pd.DataFrame(data)

print(df.head())


# Bước 2: Tiền xử lý dữ liệu
# Chuyển đổi ngày tháng thành kiểu dữ liệu ngày
df['date'] = pd.to_datetime(df['date'])


# Tạo một DataFrame để nhóm theo sản phẩm
product_data = df.groupby('products').agg({
    'amount': 'sum',
    'date': 'max'  # Lấy ngày lớn nhất cho mỗi sản phẩm
}).reset_index()


# Tính toán ma trận TF-IDF cho các sản phẩm
tfidf = TfidfVectorizer()
tfidf_matrix = tfidf.fit_transform(df['products'])

# Tính toán độ tương đồng cosine
cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

# Hàm gợi ý sản phẩm cho từng khách hàng
def get_recommendations(customer_id, num_recommendations=9):
    # Lấy danh sách sản phẩm đã mua của khách hàng
    purchased_products = df[df['customers'] == customer_id]['products'].unique()
    
    recommendations = set()
    
    # Lấy gợi ý dựa trên các sản phẩm đã mua
    for product_id in purchased_products:
        # Kiểm tra xem sản phẩm có trong ma trận không
        if product_id in tfidf.get_feature_names_out():  
            idx = np.where(tfidf.get_feature_names_out() == product_id)[0][0]
            
            # Lấy độ tương đồng với các sản phẩm khác
            sim_scores = list(enumerate(cosine_sim[idx]))
            
            # Sắp xếp sản phẩm theo độ tương đồng
            sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
            
            # Thêm sản phẩm gợi ý vào tập hợp, bỏ qua sản phẩm đã mua
            for i in sim_scores[1:]:
                if i[0] < len(tfidf.get_feature_names_out()):  # Kiểm tra chỉ số hợp lệ
                    recommendations.add(tfidf.get_feature_names_out()[i[0]])
                if len(recommendations) >= num_recommendations:
                    break

    return list(recommendations)



# Khởi tạo ứng dụng Flask
app = Flask(__name__)

# Tạo endpoint cho API
@app.route('/recommendations', methods=['GET'])
def recommendations():
    customer_id = request.args.get('customerId')  # Lấy customerId từ query params
    if customer_id is None:
        return jsonify({'error': 'customerId is required'}), 400

    # Lấy gợi ý sản phẩm cho customerId
    product_recommendations = get_recommendations(customer_id)
    print(f"Recommendations for customer {customer_id}: {recommendations}")
    return jsonify({'customerId': customer_id, 'recommendations': product_recommendations})

# Chạy ứng dụng Flask
if __name__ == '__main__':
    app.run(debug=True, port=5000)  # Chạy trên cổng 5000