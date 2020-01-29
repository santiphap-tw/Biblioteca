-- book(id, title)
-- movie(id, title)
-- checkout_item(member_id, book_id, movie_id)
-- member(id, name)

-- 1. Who checked out the book 'The Hobbit’?
SELECT 
    member.name 
FROM 
    (member JOIN checkout_item ON member.id = checkout_item.member_id) 
    JOIN book ON checkout_item.book_id = book.id
WHERE
    book.title = "The Hobbit"
;

-- 2. How many people have not checked out anything?
SELECT
    COUNT(*)
FROM 
    member
WHERE
    id NOT IN (SELECT member_id FROM checkout_item)
;

-- 3. What books and movies aren't checked out?
SELECT 
    SUM(count) 
FROM
    (
        SELECT COUNT(*) as count FROM book WHERE id NOT IN (SELECT book_id FROM checkout_item WHERE book_id NOT NULL)
        UNION ALL
        SELECT COUNT(*) as count FROM movie WHERE id NOT IN (SELECT movie_id FROM checkout_item WHERE movie_id NOT NULL)
    )
;

-- 4. Add the book 'The Pragmatic Programmer', and add yourself as a member. Check out 'The Pragmatic Programmer'. Use your query from question 1 to verify that you have checked it out. Also, provide the SQL used to update the database.
INSERT OR REPLACE INTO book VALUES (11,"The Pragmatic Programmer");
INSERT OR REPLACE INTO member VALUES (43,"Santiphap Watcharasukchit");
INSERT OR REPLACE INTO checkout_item VALUES (43,11,"");
SELECT 
    member.name 
FROM 
    (member JOIN checkout_item ON member.id = checkout_item.member_id) 
    JOIN book ON checkout_item.book_id = book.id
WHERE
    book.title = "The Pragmatic Programmer"
;

-- 5. Who has checked out more than 1 item? 
-- Tip: Research the GROUP BY syntax.
SELECT 
    member.name, checkout_count.count
FROM
    member JOIN
    (SELECT
        member_id as id, COUNT(*) as count
    FROM 
        checkout_item
    GROUP BY member_id) as checkout_count ON checkout_count.id = member.id
WHERE 
    checkout_count.count > 1
;