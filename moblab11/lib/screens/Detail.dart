import 'package:flutter/material.dart';
import 'package:moblab11/database/database.dart';
import 'package:moblab11/screens/BookList.dart';
import 'EditScreen.dart';
import 'package:moblab11/models/Book.dart';

class Detail extends StatelessWidget {
  Detail({Key key, this.book}) : super(key: key);

  // This widget is the Detail page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final Book book;

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the Detail object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(book.name),
      ),
      body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              Text("Нэр: " +
                  book.name +
                  "\n" +
                  "Зохиогч: " +
                  book.author +
                  "\n" +
                  "Хэвлэгдсэн он: " +
                  book.publishedYear.toString() +
                  "\n" +
                  "Хуудасны тоо: " +
                  book.pageNumber.toString() +
                  "\n"),
              MaterialButton(
                onPressed: () {
                  Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => EditScreen(
                                key: ObjectKey(book),
                                book: book,
                              )));
                },
                child: Text("Засварлах"),
                color: Color.fromRGBO(0, 0, 222, 1),
              ),
              MaterialButton(
                onPressed: () async {
                  await deleteBook(book);
                  Navigator.pushReplacement(context,
                      MaterialPageRoute(builder: (context) {
                    return BookList();
                  }));
                },
                child: Text("Устгах"),
                color: Color.fromRGBO(222, 0, 0, 1),
              ),
              MaterialButton(
                onPressed: () async {
                  Navigator.pushReplacement(context, MaterialPageRoute(
                    builder: (context) {
                      return BookList();
                    },
                  ));
                },
                child: Text("Буцах"),
                color: Color.fromRGBO(200, 200, 200, 1),
              )
            ],
          )),
    );
  }
}
