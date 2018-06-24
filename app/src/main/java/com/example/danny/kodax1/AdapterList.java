package com.example.danny.kodax1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danny.kodax1.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class AdapterList extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<Usuario> modeloListViews;
    ContactFilter mContactFilter;


    public AdapterList(Context context, ArrayList<Usuario> modeloListViews) {
        this.context = context;
        this.modeloListViews = modeloListViews;
    }

    @Override
    public int getCount() {
        if(modeloListViews == null){
            return 0;
        }
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

    @Override
    public Filter getFilter(){
        if(mContactFilter==null)
            mContactFilter=new ContactFilter();

        return mContactFilter;
    }

    /*public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        boolean notifyChanged = true;
    }*/

    public class ContactFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                results.values= modeloListViews;
                results.count= modeloListViews.size();

            }
            else {
                ArrayList<Usuario> filtradoDeContactos = new ArrayList<Usuario>();
                for(Usuario u : modeloListViews){
                    if (u.getNombre().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filtradoDeContactos.add(u);
                    }
                }

                results.values= filtradoDeContactos;
                results.count= filtradoDeContactos.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
         modeloListViews= (ArrayList<Usuario>) results.values;
            notifyDataSetChanged();


        }
    }
}
