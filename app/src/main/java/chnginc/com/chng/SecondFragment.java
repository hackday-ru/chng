package chnginc.com.chng;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    String friendName;

    View root;


    class GetAllFriends extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray friends = new JSONArray(result);
                for(int i = 0;i<friends.length();i++) {
                    TextView friend = new TextView(root.getContext());
                    friend.setTextSize(60);
                    friend.setGravity(Gravity.CENTER_HORIZONTAL);
                    final int userId = (int) friends.getJSONObject(i).get("id");
                    //final int taskId = (int) friends.getJSONObject(i).get("actualTask");
                    final int taskId = 4;
                    friend.setText((String)friends.getJSONObject(i).get("first_name")+"  "+(String)friends.getJSONObject(i).get("last_name"));
                    LinearLayout taskView = (LinearLayout)root.findViewById(R.id.friendLayout);
                    taskView.addView(friend);
                    friend.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent friendTaskView = new Intent(root.getContext(),ResultActivity.class);
                            friendTaskView.putExtra("friend", 12);
                            friendTaskView.putExtra("TASK_ID", taskId);
                            startActivity(friendTaskView);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //taskView.setText(result);
        }

        @Override
        protected String doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            String taskDescription="";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet("https://gentle-taiga-87228.herokuapp.com/user/getFriends?myLogin="+AppData.email);
            String responseString = "";
            try {

                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode()!=200) {
                    throw new Exception();
                }
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                //JSONObject jsonObject = new JSONObject(responseString);
                //taskDescription = jsonObject.toString();
                return responseString;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                return null;
            }
            return responseString;
        }
    }

    class AddFriend extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String result) {
            TextView friend = new TextView(root.getContext());
            friend.setText(friendName);
            friend.setTextSize(60);
            friend.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout taskView = (LinearLayout)root.findViewById(R.id.friendLayout);
            taskView.addView(friend);
            //taskView.setText(result);
        }

        @Override
        protected String doInBackground(String... params) {
            // Create a new HttpClient and Post Header
            String taskDescription="";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://gentle-taiga-87228.herokuapp.com/user/addFriend");
            String responseString = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("myLogin", AppData.email));
                nameValuePairs.add(new BasicNameValuePair("friendLogin", friendName));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode()!=200) {
                    throw new Exception();
                }
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                //JSONObject jsonObject = new JSONObject(responseString);

                return taskDescription;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                return null;
            }
            return taskDescription;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        root = rootView;
        new GetAllFriends().execute();
        Button button = (Button) rootView.findViewById(R.id.addFriend);
        friendName = ((EditText) rootView.findViewById(R.id.friendName)).getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendName = ((EditText) rootView.findViewById(R.id.friendName)).getText().toString();
                new AddFriend().execute();
            }
        });
        return rootView;
    }
}


