package com.fyp.plant;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String SelectedCatagory;
    ViewPager viewPager;
    int images[] = {R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3};
    int images2[] = {R.drawable.apple1, R.drawable.cherry1, R.drawable.corn1, R.drawable.grape1};
    MyCustomPagerAdapter myCustomPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

    }

    @SuppressLint("SetTextI18n")
    public void ready(View view) {




        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialogue);

        TextView text =  dialog.findViewById(R.id.text_dialog);

        final ImageView imageView = dialog.findViewById(R.id.imageView);


        Button dialogButton =  dialog.findViewById(R.id.btn_dialog);



        switch(view.getId()) {
            case R.id.apple:
                imageView.setImageResource(R.drawable.apple);
                SelectedCatagory="apple";
                break;
            case R.id.corn:
                imageView.setImageResource(R.drawable.corn);
                SelectedCatagory="corn";

                break;
            case R.id.potato:
                imageView.setImageResource(R.drawable.potato);
                SelectedCatagory="potato";
                break;
            case R.id.tomato:
                imageView.setImageResource(R.drawable.tomato);
                SelectedCatagory="tomato";
                break;
            case R.id.grapes:
                imageView.setImageResource(R.drawable.grapes);
                SelectedCatagory="grape";
                break;
            case R.id.orange:
                imageView.setImageResource(R.drawable.orange);
                SelectedCatagory="orange";
                break;
            case R.id.peach:
                imageView.setImageResource(R.drawable.peach);
                SelectedCatagory="peach";
                break;
            case R.id.strawberry:
                imageView.setImageResource(R.drawable.strawberry);
                SelectedCatagory="strawberry";
                break;
            case R.id.cherry:
                imageView.setImageResource(R.drawable.cherry);
                SelectedCatagory="cherry";
                break;

        }
        text.setText("Click close-up Photo of "+SelectedCatagory+" leaf which shows disease Symptoms Clearly");
        TextView help =  dialog.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);

                viewPager = dialog.findViewById(R.id.viewPagerz);
                viewPager.setVisibility(View.VISIBLE);
                myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, images2);
                viewPager.setAdapter(myCustomPagerAdapter);


            }
        });


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent init=new Intent(MainActivity.this,CameraRollActivity.class);
                startActivity(init);
                dialog.dismiss();

            }
        });
        dialog.setCancelable(true);
        dialog.show();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contact) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "plantcare@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Contact");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Suggestions :\n");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

        } else if (id == R.id.nav_share) {


            String shareBody = "The App is to be uploaded to GooglePlay";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share App"));
        }
        else if (id == R.id.nav_feedback) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Feedback");
            builder.setMessage("Help us improve our Application by sending us your suggestions and thoughts");
             final EditText input = new EditText(this);
             input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //  m_Text = input.getText().toString();
                    Toast.makeText(getApplicationContext(),input.getText().toString(),Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
