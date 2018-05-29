package com.example.jamal.vuespersonnalises;

/**
 * Created by jamal on 25/03/2018.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import android.view.MotionEvent;

import android.widget.Button;

public class ColorButton extends AppCompatButton {
    /** Liste des couleurs disponibles **/
    private TypedArray mCouleurs = null;
    /** Position dans la liste des couleurs **/
    private int position = 0;


    /**
     * Constructeur utilisé pour inflater avec un style
     */
    public ColorButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Constructeur utilisé pour inflater sans style
     */
    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructeur utilisé pour construire dans le code
     */
    public ColorButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        // Je récupère mes ressources
        Resources res = getResources();
        // Et dans ces ressources je récupère mon tableau de couleurs
        mCouleurs = res.obtainTypedArray(R.array.colors);

        setText("Changer de couleur");
    }

    /** Rectangle qui délimite le dessin */
    private Rect mRect = null;

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom)
    {
        //Si le layout a changé
        if(changed)
            //On redessine un nouveau carré en fonction des nouvelles dimensions
            mRect = new Rect(Math.round(0.75f * getWidth() - 50),
                    Math.round(0.75f * getHeight() - 50),
                    Math.round(0.75f * getWidth() + 50),
                    Math.round(0.75f * getHeight() + 50));
        //Ne pas oublier
        super.onLayout(changed, left, top, right, bottom);
    }
    /** Outil pour peindre */
    private Paint mPainter = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Uniquement si on appuie sur le bouton
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // On passe à la couleur suivante
            position ++;
            // Évite de dépasser la taille du tableau
            // (dès qu'on arrive à la longueur du tableau, on repasse à 0)
            position %= mCouleurs.length();

            // Change la couleur du pinceau
            mPainter.setColor(mCouleurs.getColor(position, -1));

            // Redessine la vue
            invalidate();
        }
        // Ne pas oublier
        return super.onTouchEvent(event);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // Dessine le rectangle à l'endroit voulu avec la couleur voulue
        canvas.drawRect(mRect, mPainter);
        // Ne pas oublier
        super.onDraw(canvas);
    }
}
