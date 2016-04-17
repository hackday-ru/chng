package chnginc.com.chng;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_cab, container, false);
        ((TextView)rootView.findViewById(R.id.userName)).setText(AppData.userName);
        ((TextView)rootView.findViewById(R.id.userSurname)).setText(AppData.userSurName);
        ((TextView)rootView.findViewById(R.id.level)).setText(AppData.level+"");
        ((TextView)rootView.findViewById(R.id.care)).setText(""+AppData.attention);
        ((TextView)rootView.findViewById(R.id.memory)).setText(""+AppData.memory);
        ((TextView)rootView.findViewById(R.id.social)).setText(""+AppData.social);
        ((TextView)rootView.findViewById(R.id.creativity)).setText(""+AppData.creativity);
        return rootView;
    }
}
