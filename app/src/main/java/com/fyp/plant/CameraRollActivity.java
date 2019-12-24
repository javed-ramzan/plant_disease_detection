package com.fyp.plant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraRollActivity extends Activity {
  private static final int CAMERA_REQUEST = 1888;
  private ImageView imageView;
  private static final int MY_CAMERA_PERMISSION_CODE = 100;

  private static final int SELECT_IMAGE = 505;
  private RecognitionScoreView resultView;
  private Bitmap bitmap;

  // Classifier
  private Classifier classifier;
  private static final int INPUT_SIZE = 224;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128.0f;
  private static final String INPUT_NAME = "input";
  private static final String OUTPUT_NAME = "final_result";
  private static final String MODEL_FILE = "file:///android_asset/optimized_mobilenet_plant_graph.pb";
  private static final String LABEL_FILE = "file:///android_asset/plant_labels.txt";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera_roll);
    Button chooseImage = (Button) findViewById(R.id.choose_image);
    resultView = (RecognitionScoreView) findViewById(R.id.results);

    resultView.setVisibility(View.INVISIBLE);

    classifier =
        TensorFlowImageClassifier.create(
            getAssets(),
            MODEL_FILE,
            LABEL_FILE,
            INPUT_SIZE,
            IMAGE_MEAN,
            IMAGE_STD,
            INPUT_NAME,
            OUTPUT_NAME);

    chooseImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
      if (data != null) {



        try {
           bitmap = (Bitmap) data.getExtras().get("data");
          imageView=findViewById(R.id.image);
          imageView.setImageBitmap(bitmap);
          bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false);

        } catch (Exception e) {
          e.printStackTrace();
        }
        classifyImage();

         
      }
    }
      if (requestCode == SELECT_IMAGE) {
        if (resultCode == Activity.RESULT_OK) {
          if (data != null) {

            Uri selectedImageURI = data.getData();
            Picasso.with(this).load(selectedImageURI).noPlaceholder().centerCrop().fit()
                .into((ImageView) this.findViewById(R.id.image));

            try
            {
              bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
              bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false);

            } catch (IOException e)
            {
              e.printStackTrace();
            }
            classifyImage();
          }


          
        } else if (resultCode == Activity.RESULT_CANCELED) {
        //  Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();

          LinearLayout lay1=findViewById(R.id.lay1);
          lay1.setVisibility(View.VISIBLE);

          LinearLayout lay2=findViewById(R.id.lay2);
          lay2.setVisibility(View.GONE);

        }
      }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void classifyImage() {
    WebView vb;
    vb= findViewById(R.id.webView);
    resultView.setVisibility(View.VISIBLE);
    final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
    resultView.setResults(results);
    String s= String.valueOf(results.get(0));
   // Toast.makeText(getApplicationContext(),s+"\n"+Home.SelectedCatagory,Toast.LENGTH_LONG).show();
    if(s.contains(MainActivity.SelectedCatagory)){

      LinearLayout lay1=findViewById(R.id.lay1);
      lay1.setVisibility(View.GONE);

      LinearLayout lay2=findViewById(R.id.lay2);
      lay2.setVisibility(View.VISIBLE);

      resultView.setResults(results);
      if(s.contains("apple apple scab")) {
         vb.loadUrl("file:///android_asset/treatment/apple scab.html");
      }else if(s.contains("apple black rot")) {
        vb.loadUrl("file:///android_asset/treatment/apple black rot.html");
      }else if(s.contains("apple cedar apple rust")) {
        vb.loadUrl("file:///android_asset/treatment/apple cedar rust.html");
      }else if(s.contains("cherry including sour powdery mildew")) {
        vb.loadUrl("file:///android_asset/treatment/cherry powdery mildew.html");
      }else if(s.contains("corn maize common rust")) {
        vb.loadUrl("file:///android_asset/treatment/corn common rust.html");
      }else if(s.contains("corn maize cercospora leaf spot gray leaf spot")) {
        vb.loadUrl("file:///android_asset/treatment/corn gray leaf spot.html");
      }else if(s.contains("corn maize northern leaf blight")) {
        vb.loadUrl("file:///android_asset/treatment/corn northern leaf blight.html");
      }else if(s.contains("grape esca black measles")) {
        vb.loadUrl("file:///android_asset/treatment/grape black measles.html");
      }else if(s.contains("grape black rot")) {
        vb.loadUrl("file:///android_asset/treatment/grape black rot.html");
      }else if(s.contains("grape leaf blight isariopsis leaf spot ")) {
        vb.loadUrl("file:///android_asset/treatment/grape leaf blight.html");
      }else if(s.contains("peach bacterial spot")) {
        vb.loadUrl("file:///android_asset/treatment/peach bacterial spot.html");
      }else if(s.contains("potato early blight")) {
        vb.loadUrl("file:///android_asset/treatment/potato early blight.html");
      }else if(s.contains("potato late blight")) {
        vb.loadUrl("file:///android_asset/treatment/potato late blight.html");
      }else if(s.contains("strawberry leaf scorch")) {
        vb.loadUrl("file:///android_asset/treatment/strawberry leaf scorch.html");
      }else if(s.contains("tomato bacterial spot")) {
        vb.loadUrl("file:///android_asset/treatment/tomato bacterial spot.html");
      }else if(s.contains("tomato early blight")) {
        vb.loadUrl("file:///android_asset/treatment/tomato early blight.html");
      }else if(s.contains("tomato late blight")) {
        vb.loadUrl("file:///android_asset/treatment/tomato late blight.html");
      }else if(s.contains("tomato leaf mold")) {
        vb.loadUrl("file:///android_asset/treatment/tomato leaf mold.html");
      }else if(s.contains("tomato septoria leaf spot")) {
        vb.loadUrl("file:///android_asset/treatment/tomato septoria leaf.html");
      }
    }else {
      LinearLayout lay1=findViewById(R.id.lay1);
      lay1.setVisibility(View.VISIBLE);

      LinearLayout lay2=findViewById(R.id.lay2);
      lay2.setVisibility(View.GONE);

      AlertDialog.Builder dialog = new AlertDialog.Builder(CameraRollActivity.this);
      dialog.setCancelable(false);
      dialog.setTitle("Invalid Image");
      dialog.setMessage("The Image You provided is either Invalid or not Clear" );
      dialog.setPositiveButton("Recapture", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {


          dialog.dismiss();
        }
      });


      final AlertDialog alert = dialog.create();
      alert.show();


    }
    vb.getSettings().setJavaScriptEnabled(true);
    vb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    vb.setWebViewClient(new WebViewClient());
    vb.getSettings().setBuiltInZoomControls(true);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public void capture(View view) {
    if (checkSelfPermission(Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[]{Manifest.permission.CAMERA},
              MY_CAMERA_PERMISSION_CODE);
    } else {
      Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(cameraIntent, CAMERA_REQUEST);



    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == MY_CAMERA_PERMISSION_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
        Intent cameraIntent = new
                Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
      } else {
        Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
      }

    }
  }


}
