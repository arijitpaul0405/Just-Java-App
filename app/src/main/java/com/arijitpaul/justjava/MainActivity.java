package com.arijitpaul.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


    // This app displays an order form to order coffee
    public class MainActivity extends AppCompatActivity {

        int quantity = 2;
        // Email ID of company
        String[] emailCompany = {"justjava00@gmail.com"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        // This method is called when the order button is clicked
        public void submitOrder(View view) {

            EditText nameField = findViewById(R.id.name_field);
            String name = nameField.getText().toString();

            CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

            CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckBox.isChecked();

            int price = calculatePrice(hasWhippedCream, hasChocolate);
            if(!name.equals("")) {
                String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, emailCompany);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
                intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            } else
                Toast.makeText(this, "Please set the Name field", Toast.LENGTH_SHORT).show();
        }

        // The method is called when the plus button is clicked
        public void increment(View view) {
            
            if(quantity == 100)
            {
                Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
                return;
            }
            
            quantity += 1;
            displayQuantity(quantity);
        }

        // The method is called when the minus button is clicked
        public void decrement(View view) {
            
            if(quantity == 1)
            {
                Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
                return;
            }
            
            quantity -= 1;
            displayQuantity(quantity);
        }

        // This method displays the given quantity value on the screen
        private void displayQuantity(int number) {
            TextView quantityTextView = findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + number);
        }

        // Calculate the price of the order
        private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

            int basePrice = 5;

            if(addWhippedCream)     basePrice += 1;
            if(addChocolate)        basePrice += 2;

            return quantity*basePrice;
        }

        // Creates summary of the order
        private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
            return getString(R.string.order_summary_name, name)
                    + "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream)
                    + "\n" + getString(R.string.order_summary_chocolate, addChocolate)
                    + "\n" + getString(R.string.order_summary_quantity, quantity)
                    + "\n" + getString(R.string.order_summary_price, price)
                    + "\n" + getString(R.string.thank_you);
        }

    }