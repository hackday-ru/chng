package chnginc.com.chng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Александр on 17.04.2016.
 */
public class ResultActivity extends Activity {


    Activity me = this;

    int taskId;

    @Bind(R.id.dislikeCounter)
    TextView disCounter;

    @Bind(R.id.likeCounter)
    TextView likeCounter;

    @Bind(R.id.previous)
    TextView backButton;

    @Bind(R.id.likeButton)
    Button likeButton;

    @Bind(R.id.dislikeButton)
    Button dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int gh = extras.getInt("friend");
            if(gh==12) {
                TextView g =((TextView)findViewById(R.id.rr));
                g.setText("");
                Drawable drawable  = getResources().getDrawable(R.drawable.mona);
                g.setBackground(drawable);
                g.setWidth(100);
                g.setHeight(100);
            }
        }

        dislikeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                disCounter.setText("1");
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                likeCounter.setText("1");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(me,UserPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
