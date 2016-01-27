package br.com.extractor.ygops.view;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentFragment extends Fragment {

    private View view;
    private static Toast toast;
    protected Activity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, int layoutId) {
        view = inflater.inflate(layoutId, container, false);
        activity = getActivity();
        return view;
    }

    protected final <T> T getElementById(int id){
        return (T)view.findViewById(id);
    }

    protected final void makeToast(int idMensagem, int duracao) {
        makeToast(getString(idMensagem), duracao);
    }

    protected final void makeToast(String mensagem, int duracao) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(activity, mensagem, duracao);
        toast.setText(mensagem);
        toast.setDuration(duracao);
        toast.show();
    }
}
