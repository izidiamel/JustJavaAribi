/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2 ;
    double price = 10.0  ;
    boolean hasCream=false,hasChoclate=false;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void reset(View view) {
        quantity=2;
        display(2);
        price=10.0;
        hasCream=false;
        hasChoclate=false;
        String text = "no one";
        String message=createOrderSummary(0,quantity,hasCream,hasChoclate,text);
        displayPriceMessage(message);

    }

    public void submitOrder(View view) {

        CheckBox cb1 = (CheckBox) findViewById(R.id.check_creem);
        CheckBox cb2 = (CheckBox) findViewById(R.id.check_choclate);
        TextView tx =(TextView) findViewById(R.id.text1);

        hasCream= cb1.isChecked();
        hasChoclate= cb2.isChecked();
        double price =  calculatePrice(quantity,hasChoclate,hasCream);
        if(tx.getText().toString().trim().length() != 0) {
            String text = tx.getText().toString();
            String message = createOrderSummary(price, quantity, hasCream, hasChoclate, text);
            displayPriceMessage(message);
        }
        else{
            String text = getString(R.string.no_name_untered);
            String message = createOrderSummary(price, quantity, hasCream, hasChoclate, text);
            displayPriceMessage(message);
        }
    }
    public void plusQuantity(View view) {
        if(quantity<=100)
        {
            quantity = quantity + 1;
            this.display(quantity);
        }
        else{

            Toast.makeText(MainActivity.this, getString(R.string.too_Much_quantity), Toast.LENGTH_SHORT).show();
        }



    }
    public void minusQuantity(View view) {
        if(quantity>=2)
        {
            quantity = quantity - 1;
            display(quantity);
        }
        else{
            Toast.makeText(MainActivity.this, "too Less quantity", Toast.LENGTH_SHORT).show();
        }


    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);



         }
    /**
     * This method displays the given message on the screen.
     */
    private void displayPriceMessage(final String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
        orderSummaryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String adress[] ={"amel.aribi@gmail.com" };
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, adress );
                intent .putExtra(Intent.EXTRA_SUBJECT, "order summary");
                intent.putExtra(Intent.EXTRA_TEXT,message );
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }
     /**
     * This method create and return a message using the given price value.
     */
    private String createOrderSummary(double price,int quantity, boolean state1,boolean state2, String text) {
        String message= text;
        message+="\n "+ getString(R.string.wipped_Cream_2, state1)   ;
        message+="\n "+ getString(R.string.choclate_2, state2)   ;
        message+="\n "+ getString(R.string.quantity) + quantity;
        message+="\n "+ getString(R.string.Total)+ price + getString(R.string.DA) ;
        message+="\n "+ getString(R.string.Thank, text);


        return message;

    }



    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private double calculatePrice(int quantity,boolean hasChoclate,boolean hasCream ) {
        double  oneCupPrice=5;
        if(hasChoclate){
            oneCupPrice+=2;
        }
        if(hasCream){
            oneCupPrice+=1;
        }
        double  price = quantity * oneCupPrice;
        return price;
    }
}