package second.test.lucassmc.academictask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaAdapterItemDisciplina extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> lista;

    public ListaAdapterItemDisciplina(Context context, ArrayList<Item> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        imageView.setImageResource(itemPosicao.getImagem());

        TextView textView = (TextView) convertView.findViewById(R.id.textView3);
        textView.setText(itemPosicao.getNome());

        TextView textViewDescricao = (TextView) convertView.findViewById(R.id.textView4);
        textViewDescricao.setText(itemPosicao.getDescricao());

        return convertView;
    }
}
