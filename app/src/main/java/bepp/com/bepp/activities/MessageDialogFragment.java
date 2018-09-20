package bepp.com.bepp.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by charlie on 20/02/18.
 */

public class MessageDialogFragment extends DialogFragment {
    public static MessageDialogFragment newInstance(int title, String message) {
        MessageDialogFragment fragment = new MessageDialogFragment();

        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("message", message);

        fragment.setArguments(args);

        return fragment;
    }

    public MessageDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String message = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }
}