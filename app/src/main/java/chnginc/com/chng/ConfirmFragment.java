package chnginc.com.chng;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 17.04.2016.
 */
public class ConfirmFragment extends Fragment {

    View root;

    boolean taskCompleted = false;

    class SubmitTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String result) {
            ((TextView)root.findViewById(R.id.taskDesc)).setText("Выполнено!");
            AppData.wasInit = true;
        }

        @Override
        protected String doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            String taskDescription="";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://gentle-taiga-87228.herokuapp.com/task/done");
            String responseString = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("login", AppData.email));
                nameValuePairs.add(new BasicNameValuePair("id", ""+AppData.TaskId));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Add your data
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                if(response.getStatusLine().getStatusCode()!=200) {
                    throw new Exception();
                }
                return responseString;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
            return responseString;
        }
    }

    class GetTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String result) {
            TextView taskView = (TextView)root.findViewById(R.id.taskDesc);
            taskView.setText(result);
        }

        @Override
        protected String doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            String taskDescription="";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet("https://gentle-taiga-87228.herokuapp.com/task/getTask?login="+"test@test.ru");
            String responseString = "";
            try {
                // Add your data
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

                JSONObject jsonObject = new JSONObject(responseString);
                if((int)(jsonObject.get("level"))==-1) {
                    taskCompleted = true;
                }
                taskDescription = (String) jsonObject.get("description");

                return taskDescription;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return taskDescription;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView uploadImage = (ImageView) root.findViewById(R.id.uploadImage);
            uploadImage.setImageBitmap(imageBitmap);
            new SubmitTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.activity_confirm, container, false);
        root = rootView;
        if(AppData.wasInit) {
            Intent n = new Intent(root.getContext(),ResultActivity.class);
            startActivity(n);
        }
        new GetTask().execute();
        final Button button = (Button)rootView.findViewById(R.id.upload);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = MainActivity.getOutputMediaFile(1);
                String fileUri = file.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, 100);

                //startActivity(intent);
                button.setEnabled(true);

            }
        });

        return rootView;
    }



}
