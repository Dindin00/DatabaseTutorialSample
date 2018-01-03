package tw.idv.dindin00.databasetutorialsample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    //用來顯示結果的ListView宣告
    ListView lv_show;
    //SQLite資料庫變數宣告
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //資料庫初始化
        //第一個參數是命名資料庫名稱(副檔名為.db)
        //第二個參數是設定資料庫存取權限為私有，即只有此APP可以存取
        //第三個參數是針對錯誤情況的處理，若不想自行特別處理就放置null
        database = openOrCreateDatabase("test.db", MODE_PRIVATE, null);
        //利用execSQL執行SQL的DDL及部分DML指令
        //透過if not exists只要已經有該資料表存在就不會重複創建
        //主鍵的名稱必須為_id
        database.execSQL("create table if not exists students(_id integer primary key, name text not null, class text not null)");
        //插入三筆資料
        //注意SQL語法中如果有要採用字串時必須與最外層Android字串包覆的引號不同
        //如本範例外層android的是雙引號，而內部SQL語法為單引號
        database.execSQL("insert into students values(10510108, '王小明', '資二A')");
        database.execSQL("insert into students values(10510215, '鄭成功', '資二B')");
        database.execSQL("insert into students values(10410345, '陳筱玲', '資三C')");

        //透過rawQuery執行SQL的select語法查詢資料
        //再利用Cursor儲存查詢出來的結果
        //第一個參數為查詢的SQL語法
        //第二個參數是如果要透過android動態傳入參數至SQL與法中所需要使用的參數，若無傳入則填null即可
        //若有要傳入參數則在SQL語法中輸入?，再依序傳入即可如下所示
        // Cursor cursor = database.rawQuery("select * from ?", new String[]{"students"});
        Cursor cursor = database.rawQuery("select * from students", null);
        //將查詢結果(cursor)放入Adapter中用以放入ListView顯示
        //第一個參數為要顯示資料的activity的context
        //第二個參數為ListView中每一列要套用的樣式，本範例採用android預設的雙列樣式，也可自行設計layout
        //第三個參數為要用來顯示的SQL查詢結果
        //第四個參數為要顯示的欄位名稱
        //第五個參數為要在每一列的哪個預設位置顯示，若自行設定layout則採用R.id.xxx即可
        //第六個參數用於監聽特殊處理之用，若不需使用輸入0即可
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, cursor, new String[]{"name", "class"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        //ListView初始化
        lv_show = (ListView) findViewById(R.id.lv_show);
        //設定ListView的資料內容(Adapter)
        lv_show.setAdapter(cursorAdapter);

    }
}
