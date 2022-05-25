package com.example.courseworkapplication.db_controller.filler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.courseworkapplication.db_controller.FlightReaderContract;
import com.example.courseworkapplication.db_controller.HotelDBHelper;
import com.example.courseworkapplication.db_controller.HotelReaderContract;

public class HotelDBFiller {
    private HotelDBHelper mHotelDBHelper;
    private String[][] hotelsData = {
            {"Paris, France", "Avia Hôtel Saphir Montparnasse", "https://goo.gl/maps/kKMiP9qUYV28AyhF9", "197", "3", "https://media-cdn.tripadvisor.com/media/photo-s/07/0d/c4/c8/avia-montparnasse-hotel.jpg"},
            {"Paris, France", "Novotel Suites Paris Montreuil Vincennes", "https://goo.gl/maps/dcLZ5d9fH4B6pHGMA", "279", "4", "https://media-cdn.tripadvisor.com/media/photo-s/1c/d4/43/4c/exterior-view.jpg"},
            {"Paris, France", "L'Empire Paris", "https://goo.gl/maps/SQYHbX3JTzAAQAhe6", "325", "4", "https://media-cdn.tripadvisor.com/media/photo-s/04/94/89/33/l-empire-paris.jpg"},
            {"Paris, France", "Hotel Scarlett", "https://goo.gl/maps/D487yEi4keYnwkLPA", "237", "3", "https://media-cdn.tripadvisor.com/media/photo-s/0b/97/1f/f3/hotel-scarlett.jpg"},
            {"Rome, Italy", "Hotel Relais Dei Papi", "https://goo.gl/maps/UMkKyr5zy8T7T8Gm7", "387", "3", "https://dynamic-media-cdn.tripadvisor.com/media/photo-s/02/e8/48/dc/relais-dei-papi.jpg?w=600&h=-1&s=1"},
            {"Rome, Italy", "Hotel Borromeo", "https://goo.gl/maps/dvTXs4JhC6vLjbyV8", "465", "3", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/02/47/bb/1d/borromeo-hotel.jpg?w=1000&h=-1&s=1"},
            {"Rome, Italy", "Rome Central Rooms", "https://g.page/RomeCentralRooms?share", "124", "4", "https://media-cdn.tripadvisor.com/media/photo-m/1280/15/60/a4/21/pantheon-room-camera.jpg"},
            {"Rome, Italy", "Hotel Pacific", "https://g.page/PacificHotelRoma?share", "149", "3", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1c/f8/47/26/hotel-pacific-roma.jpg?w=1000&h=-1&s=1"},
            {"Moscow, Russia", "Отель St. Regis", "https://g.page/the-st--regis-moscow-nikolskaya?share", "733", "5", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/194428535.jpg?k=1ce1d4d1e8729f328f35f5975ab8144742e87fea8b11bbc6414a011133da599b&o=&hp=1"},
            {"Moscow, Russia", "Отель Mercure", "https://goo.gl/maps/6ACa6yZ7MRCMVqGi7", "61", "4", "https://media-cdn.tripadvisor.com/media/photo-s/1c/d9/90/a6/exterior-view.jpg"},
            {"Moscow, Russia", "Гостиница Метрополь", "https://goo.gl/maps/Wk3rFEAsNuoD1PfLA", "408", "5", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/335205641.jpg?k=2308fba2d7e1185d3e7e8d22c8ba206a81d196e90bbc901a197e6b0d2577c994&o=&hp=1"},
            {"Moscow, Russia", "Отель Ibis", "https://g.page/ibis-moscow-kievskaya?share", "51", "3", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/252335333.jpg?k=87b1533553c64acee2d5aacbf0e93dcd1726c170ccb9980f0a97de64090621f6&o=&hp=1"},
            {"Saint Petersburg, Russia", "Отель Гельвеция", "https://goo.gl/maps/Noy8X7w7uj9xsxg57", "262", "5", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/278534132.jpg?k=574c2e2c1430993260f6408ff8e65f7446db607df38da0bc0b6d3d266aa86d6b&o=&hp=1"},
            {"Saint Petersburg, Russia", "Отель Пушка ИНН", "https://goo.gl/maps/ivt13d1HTe9erCtZ6", "174", "4", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0e/a0/f7/58/pushka-inn-hotel.jpg?w=900&h=-1&s=1"},
            {"Saint Petersburg, Russia", "Домина Санкт-Петербург", "https://goo.gl/maps/qd9QHkQjC1QJk4q97", "185", "5", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1d/0b/8c/13/caption.jpg?w=900&h=-1&s=1"},
            {"Saint Petersburg, Russia", "Мери Отель", "https://goo.gl/maps/EZJ1X3KHh65qtzao6", "60", "3", "https://www.maryhotelgroup.com/upload/iblock/99e/99ed06ded31dd886c78fc6e15251e7c2.jpg"},
            {"Saint Petersburg, Russia", "The Faces Petrogradskay", "https://goo.gl/maps/QBbR3sCaw1nY5aiq9", "85", "3", "https://media-cdn.tripadvisor.com/media/photo-s/10/bd/e2/59/the-faces.jpg"},
            {"Tomsk, Russia", "Гостиница Эдем", "https://goo.gl/maps/ZfPGvhXZ3XyeyjVB8", "21", "3", "https://media-cdn.tripadvisor.com/media/photo-s/05/18/8e/9c/caption.jpg"},
            {"Tomsk, Russia", "Кухтерин Отель", "https://g.page/kuhterinhotel?share", "108", "5", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1c/97/a1/48/central-hall.jpg"},
            {"Tomsk, Russia", "Рубин конгресс-центр", "https://goo.gl/maps/dVVBWZscZKkxsgeR7", "25", "3", "https://rubin.tomsk.ru/wp-content/uploads/2018/03/2.jpg"},
            {"Tomsk, Russia", "Ксандер Отель", "https://goo.gl/maps/8G3HhzUf3SU5p5WX8", "85", "5", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/32/a1/6e/xander-hotel-tomsk.jpg?w=900&h=-1&s=1"},
            {"Tomsk, Russia", "Тоян", "https://goo.gl/maps/Yz7P8RZ7ZdxepKFx5", "108", "4", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/54266014.jpg?k=be09703e978c9ba03cf5ce9c35238b269e846077e6da2f06ff95d524e2365c59&o=&hp=1"},
            {"Tomsk, Russia", "Сибирь", "https://goo.gl/maps/PLkdny1UYDgBAtMW7", "60", "3", "https://media-cdn.tripadvisor.com/media/photo-s/0e/f6/72/6f/caption.jpg"},
            {"Tomsk, Russia", "Гостиница Томск", "https://goo.gl/maps/Vz6kNsDY2jwQ4RB68", "49", "3", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/07/ab/a0/cc/caption.jpg?w=1200&h=-1&s=1"},
            {"Tomsk, Russia", "Отель «Элегант»", "https://goo.gl/maps/jZRPhJT2sCHLmPj29", "55", "4", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/40507938.jpg?k=5c7fa5651d530f36729d54974c6efdaeabce216d27892c3183473c18ecd6368b&o=&hp=1"},
            {"Tomsk, Russia", "Спорт-Отель", "https://goo.gl/maps/tpd9L94JqQr5usGE6", "49", "3", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/73276179.jpg?k=aeeaf9bd785b4612dede5a22ed72ea985e33c267963f028a94a8f83e15daad2c&o=&hp=1"},
            {"Tomsk, Russia", "Магистрат", "https://goo.gl/maps/Pgu4RsQWmVVtzBhh6", "102", "4", "https://cf.bstatic.com/xdata/images/hotel/max1024x768/277859912.jpg?k=a0355af948f49d336deceea0ae5d6d881daef3884b2bcbdec47cf67218a17fdb&o=&hp=1"},
    };

    public HotelDBFiller(HotelDBHelper hotelDBHelper) {
        mHotelDBHelper = hotelDBHelper;
    }

    private void addRow(String[] data) {
        SQLiteDatabase database = mHotelDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HotelReaderContract.HotelEntry.CITY, data[0]);
        values.put(HotelReaderContract.HotelEntry.HOTEL_NAME, data[1]);
        values.put(HotelReaderContract.HotelEntry.MAP_LINK, data[2]);
        values.put(HotelReaderContract.HotelEntry.COST, data[3]);
        values.put(HotelReaderContract.HotelEntry.RATING, data[4]);
        values.put(HotelReaderContract.HotelEntry.IMAGE_URL, data[5]);

        long newRowId = database.insert(
                HotelReaderContract.HotelEntry.TABLE_NAME,
                null,
                values);
    }

    public void fillDB() {
        for (int i = 0; i < hotelsData.length; i++) {
            addRow(hotelsData[i]);
        }
    }
}
