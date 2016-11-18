package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText userName = (EditText) findViewById(R.id.name_field);
        String name = userName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
      //  displayMessage(summary);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject,name));
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether user wants whipped cream toppings or not
     * @param addChocolate    is whether user wants chocolate toppings or not
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;

        }
        if (addChocolate) {
            basePrice += 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    /**
     * Creates order summary
     *
     * @param price           of the order
     * @param addWhippedCream is whether the customer wants whipped cream toppings or not
     * @param addChocolate    is whether the customer wants chocolate toppings or not
     * @param name            of the user
     * @return summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String summary = getString(R.string.order_summary_name,name);
        summary += "\n" + getString(R.string.order_summary_whipped_cream,addWhippedCream);
        summary += "\n" + getString(R.string.order_summary_chocolate,addChocolate);
        summary += "\n" + getString(R.string.order_summary_quantity,quantity);
        summary += "\n" + getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        summary += "\n" + getString(R.string.thank_you);
        return summary;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    /*private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
*/
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.max_coffee), Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.min_coffee), Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
        }
        display(quantity);
    }
}