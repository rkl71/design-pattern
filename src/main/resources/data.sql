insert into product_item(id, name, pid)
values (1, '商城', 0),
       (2, '电脑', 1),
       (3, '书籍', 1),
       (4, '台式电脑', 2),
       (5, '笔记本电脑', 2),
       (6, '游戏电脑', 4),
       (7, '办公电脑', 4),
       (8, '教育类', 3),
       (9, '科普类', 3),
       (10, '九年义务教育书籍', 8);

insert into business_launch(id, business_detail, target_city, target_sex, target_product)
values (1, '苹果电脑投放业务', '', '', 'computer,phone'),
       (2, '某奢侈品投放业务', '', 'F', 'Female bag'),
       (3, '北方某店投放业务', 'bj,tj', '', ''),
       (4, '平台优惠卷', '', '', '');

insert into products(id, product_id, send_red_bag)
values (1, '100', 0),
       (2, '101', 1);