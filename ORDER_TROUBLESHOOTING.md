# Order Items Not Showing - Troubleshooting Guide

## What I Fixed

### 1. **Corrected SQL Statement**
The original code had `order_date` in the INSERT statement, but the database schema has it as `DEFAULT CURRENT_TIMESTAMP`, so it shouldn't be in the INSERT.

**Before (WRONG):**
```sql
INSERT INTO Orders (user_id, total, status, order_date) VALUES (?, ?, ?, NOW())
```

**After (CORRECT):**
```sql
INSERT INTO Orders (user_id, total, status) VALUES (?, ?, ?)
```

### 2. **Added Debug Logging**
The code now prints detailed information to the console when you checkout:
- User ID
- Total amount
- Number of items
- Each product being added
- Order ID generated
- Number of items inserted

## How to Test & Debug

### Step 1: Run the Application
```bash
.\mvnw.cmd clean javafx:run
```

### Step 2: Add Items and Checkout
1. Login with any user
2. Add products to cart
3. Go to cart and click "Checkout"
4. **Watch the console output** - you should see:
```
=== CHECKOUT DEBUG ===
User ID: 1
Total: ₹2598.00
Items in cart: 3
Order insert rows affected: 1
Generated Order ID: 4
Adding item: Logitech Mouse M235 (ID: 1) - ₹899.00
Adding item: HP Keyboard K150 (ID: 2) - ₹699.00
Adding item: Samsung Earbuds S23 (ID: 5) - ₹1999.00
Order items inserted: 3
=== CHECKOUT SUCCESS ===
```

### Step 3: Check MySQL Database

**Check Orders:**
```sql
SELECT * FROM Orders ORDER BY order_id DESC LIMIT 5;
```

**Check Order Items:**
```sql
SELECT 
    oi.order_item_id,
    oi.order_id,
    p.name as product_name,
    oi.quantity,
    oi.price_at_purchase,
    o.total as order_total,
    o.status,
    o.order_date
FROM Order_Items oi
JOIN Products p ON oi.product_id = p.product_id
JOIN Orders o ON oi.order_id = o.order_id
ORDER BY oi.order_item_id DESC
LIMIT 10;
```

**Check Complete Order Details:**
```sql
SELECT 
    o.order_id,
    o.order_date,
    u.username,
    p.name as product_name,
    oi.quantity,
    oi.price_at_purchase,
    o.total as order_total,
    o.status
FROM Orders o
JOIN Users u ON o.user_id = u.user_id
JOIN Order_Items oi ON o.order_id = oi.order_id
JOIN Products p ON oi.product_id = p.product_id
ORDER BY o.order_id DESC, oi.order_item_id;
```

## Common Issues & Solutions

### Issue 1: "Access Denied" Error
**Solution:** Update your database password in `src/main/java/app/db/Database.java`
```java
private static final String PASS = "YOUR_PASSWORD_HERE";
```

### Issue 2: No Console Output
**Solution:** Make sure you're running from the terminal/command prompt, not from an IDE that might hide console output.

### Issue 3: Database Not Found
**Solution:** Run the SQL script first:
```bash
mysql -u root -p < sql/sample-data.sql
```

### Issue 4: Foreign Key Constraint Error
**Solution:** Make sure the `user_id` exists in the Users table. The code defaults to user_id=1 if no user is logged in.

## What to Look For

✅ **Success Indicators:**
- Console shows "=== CHECKOUT SUCCESS ==="
- UI shows "✓ Order #X placed successfully!"
- MySQL shows new rows in both Orders and Order_Items tables

❌ **Failure Indicators:**
- Console shows "=== CHECKOUT ERROR ==="
- UI shows "Checkout failed: [error message]"
- Check the error message for details

## Database Schema Reference

**Orders Table:**
- order_id (AUTO_INCREMENT PRIMARY KEY)
- user_id (INT, FOREIGN KEY to Users)
- order_date (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- total (DECIMAL)
- status (ENUM: 'pending', 'processing', 'shipped', 'delivered', 'cancelled')

**Order_Items Table:**
- order_item_id (AUTO_INCREMENT PRIMARY KEY)
- order_id (INT, FOREIGN KEY to Orders)
- product_id (INT, FOREIGN KEY to Products)
- quantity (INT)
- price_at_purchase (DECIMAL)

## Next Steps

1. Run the application
2. Try to checkout
3. Check the console output
4. Run the SQL queries above
5. If still not working, share the console output with me!
