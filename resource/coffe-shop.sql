CREATE DATABASES shop_coffee;
USE shop-coffee;

CREATE TABLE permission (
  id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE `role` (
  id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permissions (
  role_id VARCHAR(255) NOT NULL,
   permissions_id VARCHAR(255) NOT NULL,
   CONSTRAINT pk_role_permissions PRIMARY KEY (role_id, permissions_id)
);

ALTER TABLE role_permissions ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permissions_id) REFERENCES permission (id);

ALTER TABLE role_permissions ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);


CREATE TABLE customer (
  id VARCHAR(255) NOT NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   full_name VARCHAR(255) NULL,
   age INT NULL,
   gender BIT(1) NOT NULL,
   created_date date NULL,
   modified_date date NULL,
   role_id VARCHAR(255) NOT NULL,
   menberlv SMALLINT NULL,
   point INT NULL,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE customer ADD CONSTRAINT uc_customer_role UNIQUE (role_id);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

CREATE TABLE employee (
  id VARCHAR(255) NOT NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   full_name VARCHAR(255) NULL,
   age INT NULL,
   gender BIT(1) NOT NULL,
   created_date date NULL,
   modified_date date NULL,
   role_id VARCHAR(255) NOT NULL,
   employeelv SMALLINT NULL,
   salary DOUBLE NOT NULL,
   work_time SMALLINT NULL,
   CONSTRAINT pk_employee PRIMARY KEY (id)
);

ALTER TABLE employee ADD CONSTRAINT uc_employee_role UNIQUE (role_id);

ALTER TABLE employee ADD CONSTRAINT FK_EMPLOYEE_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);


CREATE TABLE category (
  id VARCHAR(255) NOT NULL,
   category_name VARCHAR(255) NULL,
   CONSTRAINT pk_category PRIMARY KEY (id)
);


CREATE TABLE product (
  id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NULL,
   price DOUBLE NOT NULL,
   `description` VARCHAR(255) NULL,
   category_id VARCHAR(255) NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

CREATE TABLE order_line (
  id VARCHAR(255) NOT NULL,
   amount INT NULL,
   product_id VARCHAR(255) NOT NULL,
   order_id VARCHAR(255) NOT NULL,
   CONSTRAINT pk_orderline PRIMARY KEY (id)
);

ALTER TABLE order_line ADD CONSTRAINT uc_orderline_product UNIQUE (product_id);

ALTER TABLE order_line ADD CONSTRAINT FK_ORDERLINE_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_line ADD CONSTRAINT FK_ORDERLINE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);


CREATE TABLE orders (
  id VARCHAR(255) NOT NULL,
   total_price DOUBLE NOT NULL,
   created_date date NULL,
   modified_date date NULL,
   order_type SMALLINT NULL,
   customer_id VARCHAR(255) NULL,
   employee_id VARCHAR(255) NULL,
   CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

CREATE TABLE payment (
  id VARCHAR(255) NOT NULL,
   payment_type VARCHAR(255) NULL,
   customer_id VARCHAR(255) NULL,
   employee_id VARCHAR(255) NULL,
   order_id VARCHAR(255) NOT NULL,
   CONSTRAINT pk_payment PRIMARY KEY (id)
);

ALTER TABLE payment ADD CONSTRAINT uc_payment_order UNIQUE (order_id);

ALTER TABLE payment ADD CONSTRAINT FK_PAYMENT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE payment ADD CONSTRAINT FK_PAYMENT_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE payment ADD CONSTRAINT FK_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);