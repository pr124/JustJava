package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity++;
        if(quantity>100) {
            quantity = 100;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity--;
        if(quantity<1) {
            quantity = 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editName = (EditText) findViewById(R.id.name);
        String name = editName.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));               // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param priceWhippedCream boolean for whipped cream passed and given +1 to price
     * @param priceChocolate boolean for chocolate passed and given +2 to price
     * @return total price
     */
    private int calculatePrice(boolean priceWhippedCream, boolean priceChocolate) {
        int price = 5;
        if(priceWhippedCream) {
            price += 1;                     // += shorthand for price = price + 1;
        }
        if(priceChocolate == true) {
            price += 2;
        }
        return price * quantity;
    }

    /**
     * @param name
     * @param addWhippedCream
     * @param addChocolate
     * @param price of the order
     * @return text of the order summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String orderSummary = "Name: " + name;
        orderSummary += "\nAdd whipped cream? " + addWhippedCream;
        // += is shorthand for orderSummary = orderSummary + ....
        orderSummary += "\nAdd chocolate? " + addChocolate;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: $" + price;
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}