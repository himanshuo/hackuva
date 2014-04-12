package com.hackuva.menuclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class GenericAlertNotification extends DialogFragment
{
	public Dialog onCreateDialog(Bundle doesAnyoneActuallyUseTheSavedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		
		String title = this.getArguments().getString("title");
		String message = this.getArguments().getString("message");
		
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		
		builder.setPositiveButton("Nooooooooo!", null);
		
		return builder.create();
	}
}
