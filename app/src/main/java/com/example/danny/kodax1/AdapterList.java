package com.example.danny.kodax1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class AdapterList extends BaseAdapter {

    private Context context;
    private ArrayList<Usuario> modeloListViews;

    public AdapterList(Context context, ArrayList<Usuario> modeloListViews) {
        this.context = context;
        this.modeloListViews = modeloListViews;
    }

    @Override
    public int getCount() {
        return modeloListViews.size();
    }

    @Override
    public Object getItem(int i) {
        return modeloListViews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.list_item,null);
        }

        final TextView nombre = view.findViewById(R.id.nombre_item);
        TextView clinica = view.findViewById(R.id.clinica_item);
        TextView id_usuario = view.findViewById(R.id.id_usuario_item);

        Usuario usuario = modeloListViews.get(i);

        nombre.setText(usuario.getNombre());
        clinica.setText(usuario.getNombreClinica());
        id_usuario.setText(""+usuario.getId());

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
