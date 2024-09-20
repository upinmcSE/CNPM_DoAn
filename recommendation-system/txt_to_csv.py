import csv
import re

# Đọc dữ liệu từ file txt
input_file = '../backend/order.txt'
output_file = 'order_log.csv'

# Định dạng của mỗi dòng trong file txt
line_pattern = re.compile(r"Customer: (.+?), Product: (.+?), Amount: (\d+), Date: (\d{4}-\d{2}-\d{2})")

# Mở file txt và đọc dữ liệu
with open(input_file, 'r', encoding='utf-8') as txt_file:
    lines = txt_file.readlines()

# Mở file csv để ghi dữ liệu
with open(output_file, 'w', newline='', encoding='utf-8') as csv_file:
    writer = csv.writer(csv_file)
    
    # Viết tiêu đề cột vào file csv
    writer.writerow(['customer', 'product', 'amount', 'date'])
    
    # Xử lý từng dòng trong file txt
    for line in lines:
        match = line_pattern.match(line.strip())
        if match:
            customer, product, amount, date = match.groups()
            # Viết dữ liệu vào file csv
            writer.writerow([customer, product, amount, date])

print(f"Dữ liệu đã được thêm vào file : {output_file}")
