package corpode21.com.br.corpod21;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.fragments.AlertaRefeicaoFragment;
import corpode21.com.br.corpod21.fragments.BonusFragment;
import corpode21.com.br.corpod21.fragments.CardapioFragment;
import corpode21.com.br.corpod21.fragments.ContatoFragment;
import corpode21.com.br.corpod21.fragments.DesafioSemanaFragment;
import corpode21.com.br.corpod21.fragments.DuvidasFrequentesFragment;
import corpode21.com.br.corpod21.fragments.EvolucaoFragment;
import corpode21.com.br.corpod21.fragments.MedidasFragment;
import corpode21.com.br.corpod21.fragments.ModulosFragment;

/**
 * Created by Fabio on 09/04/2015.
 */
public class MainActivity extends ActionBarActivity {

    /*public static final int MENU_MODULOS = 1;
    public static final int MENU_BONUS = 2;
    public static final int MENU_DESAFIO_SEMANA = 3;
    public static final int MENU_CARDAPIO = 4;
    public static final int MENU_EVOLUCAO = 5;
    public static final int MENU_ALERTAS = 6;
    public static final int MENU_DUVIDAS = 7;
    public static final int MENU_CONTATO = 8;
    public static final int MEDIDAS = 9;

    String TITLES[] = {"Módulos","Bônus","Desafio da Semana","Cardápios", "Evolução", "Alertas","Dúvidas Frequentes","Contato"};*/

    public static final int MENU_MODULOS = 1;
    public static final int MENU_BONUS = 2;
    public static final int MENU_CARDAPIO = 3;
    public static final int MENU_EVOLUCAO = 4;
    public static final int MENU_CONTATO = 5;
    public static final int MEDIDAS = 6;

    String TITLES[] = {"Módulos","Bônus","Alimentação", "Evolução","Contato"};
    int ICONS[] = { R.drawable.ic_home, R.drawable.ic_events,R.drawable.ic_events, R.drawable.ic_events,R.drawable.ic_travel };

    String NAME ;
    String EMAIL ;
    String PROFILE ;
    private String FIRST_TIME;

    private Toolbar mToolBar;                             // Declaring the Toolbar Object
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        if (!session.isLoggedIn()) {
            finish();
        }
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        NAME = user.get(SessionManager.KEY_NAME);
        EMAIL = user.get(SessionManager.KEY_EMAIL);
        PROFILE = user.get(SessionManager.KEY_FOTO);
        FIRST_TIME = user.get(SessionManager.KEY_FIRST_TIME);

        setSupportActionBar(mToolBar);
        setupDefaultNavDrawer();

        if(FIRST_TIME != null)
            if(FIRST_TIME.equals("1"))
                showInicioMedidas();


        int FragmentId = getIntent().getIntExtra("FRAGMENT_ID",999);

        switch (FragmentId)
        {
            case 4:
                selectItemMenu(MENU_EVOLUCAO);
                break;
            case 5:
                selectItemMenu(MENU_CARDAPIO);
                break;

            default:
                selectItemMenu(MENU_MODULOS);
                break;
        }
    }

    private void showInicioMedidas()
    {
        new AlertDialog.Builder(this)
                .setTitle("Bem vindo ao programa CorpoD21")
                .setMessage("Deseja começar a acompanhar seu peso e medidas ?")
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goActivity(MedidasActivity.class);
                    }
                })
                .setNegativeButton(R.string.mais_tarde, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_notification_c21)
                .show();

        session.setUserDetails(session.KEY_FIRST_TIME, "0");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initActionBarToolbar();
        setBarTitle(getString(R.string.modulos));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            session.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------
    // Toolbar
    //----------------------------------------------------------------------------

    protected void initActionBarToolbar() {
        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.tool_bar);
            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
            }
        }
    }

    public void setActionBarBackArrow()
    {
        mToolBar.setNavigationIcon(R.mipmap.ic_up21);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setBarTitle(String s)
    {
        getSupportActionBar().setTitle(s);
    }


    //----------------------------------------------------------------------------
    // Navigation Drawer
    //----------------------------------------------------------------------------

    public void setupDefaultNavDrawer() {
        // What nav drawer item should be selected?
        //int selfItem = getSelfNavDrawerItem();

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    //Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    selectItemMenu(recyclerView.getChildPosition(child));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,mToolBar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }


    public void selectItemMenu(int position) {
        // update the main content by replacing fragments
        switch (position)
        {
            case 0:
                break;
            case MENU_MODULOS:
                openFragment(new ModulosFragment());
                break;
            case MENU_BONUS:
                openFragment(new BonusFragment());
                break;
            case MENU_EVOLUCAO:
                openFragment(new EvolucaoFragment());
                break;
            case MENU_CARDAPIO:
                openFragment(new CardapioFragment());
                break;
            case MENU_CONTATO:
                openFragment(new ContatoFragment());
                break;
            case MEDIDAS:
                openFragment(new MedidasFragment());
                break;

            /*case MENU_DESAFIO_SEMANA:
                openFragment(new DesafioSemanaFragment());
                break;
            case MENU_DUVIDAS:
                openFragment(new DuvidasFrequentesFragment());
                break;
            case MENU_ALERTAS:
                openFragment(new AlertaRefeicaoFragment());
                break;*/

        }

        if(position != 0) {
            if(position == MEDIDAS)
                mToolBar.setTitle("Atualizar Medidas");
            else
                mToolBar.setTitle(TITLES[position - 1]);
            // update selected item and title, then close the drawer
            //mDrawerList.setItemChecked(position, true);
            //mDrawerLayout.closeDrawer(mDrawerList);
        }
        Drawer.closeDrawers();
    }


    private void goActivity(Class c) {

        Intent it = new Intent(this.getApplicationContext(), c);
        startActivity(it);
    }

    private void openFragment(final Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, fragment)
                .commit();
        //Passando parametros para o Fragment
        if(FIRST_TIME == "1") {
            Bundle args = new Bundle();
            args.putInt("ID_SEMANA", 1);
            fragment.setArguments(args);
        }
    }

    //Voltar uma tela ao clicar no botao back
    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");

            getSupportFragmentManager().popBackStack();
        } else {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.titulo_sair)
                    .setMessage(R.string.saida_confirmacao)
                    .setNegativeButton(R.string.nao, null)
                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }
}
