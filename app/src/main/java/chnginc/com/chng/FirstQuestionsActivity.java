package chnginc.com.chng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Александр on 16.04.2016.
 */
public class FirstQuestionsActivity extends AppCompatActivity {

    @Bind(R.id.submitButton)
    Button submitButton;

    Activity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_questions);
        ButterKnife.bind(this);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(me, UserPageActivity.class);
                startActivity(intent);
            }
        });



    }

}
