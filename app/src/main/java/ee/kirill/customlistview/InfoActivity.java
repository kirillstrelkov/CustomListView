package ee.kirill.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoActivity extends Activity {

    public static final int BAD_IMAGE_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        String capital = intent.getStringExtra("capital");
        int imageId = intent.getIntExtra("imageId", BAD_IMAGE_ID);

        TextView countyView = (TextView) findViewById(R.id.infoCountry);
        countyView.setText(country);

        TextView capitalText = (TextView) findViewById(R.id.infoCapital);
        capitalText.setText(capital);

        if (imageId != BAD_IMAGE_ID) {
            ImageView imageView = (ImageView) findViewById(R.id.infoImgView);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
            imageView.setImageBitmap(bitmap);
        }
    }
}
