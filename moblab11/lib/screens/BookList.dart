import 'package:flutter/material.dart';
import 'package:moblab11/database/database.dart';
import 'package:moblab11/models/Book.dart';
import 'package:moblab11/screens/Detail.dart';
import 'package:moblab11/screens/EditScreen.dart';
import 'package:permission_handler/permission_handler.dart';

class BookList extends StatefulWidget {
  BookList({Key key}) : super(key: key);

  // This widget is the BookList page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  @override
  _BookListState createState() => _BookListState();
}

class _BookListState extends State<BookList> {
  TextEditingController _controller;
  String _searchText = "";
  void _search_() {
    setState(() {
      _searchText = _controller.text;
      _books = getBooks(filterer: _searchText);
    });
  }

  @override
  void initState() {
    super.initState();
    requestReadExternalStorage();
    _controller = TextEditingController();
    setState(() {
      _books = getBooks();
    });
  }

  Future<List<Book>> _books;

  void requestReadExternalStorage() async {
    final status = await Permission.manageExternalStorage.request();

    if (status == PermissionStatus.granted) {
      print('Permission Granted');
    } else if (status == PermissionStatus.denied) {
      print('Permission denied');
    } else if (status == PermissionStatus.permanentlyDenied) {
      print('Permission Permanently Denied');
    }

    _books = getBooks();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _books,
      builder: (context, snapshot) {
        if (!snapshot.hasError) {
          return Scaffold(
              appBar: AppBar(
                // Here we take the value from the BookList object that was created by
                // the App.build method, and use it to set our appbar title.
                title: Text("All books"),
                actions: [
                  PopupMenuButton<String>(
                    onSelected: (data) {
                      Navigator.pushReplacement(context, new MaterialPageRoute(
                        builder: (context) {
                          return EditScreen();
                        },
                      ));
                    },
                    itemBuilder: (BuildContext context) {
                      return {'AddBook'}.map((String choice) {
                        return PopupMenuItem<String>(
                          value: choice,
                          child: Text(choice),
                        );
                      }).toList();
                    },
                  ),
                ],
              ),
              body: Column(
                children: [
                  Row(
                    children: [
                      Expanded(
                          child: TextFormField(
                        controller: _controller,
                        decoration: InputDecoration(
                            suffixIcon: IconButton(
                          icon: Icon(Icons.search),
                          onPressed: () {
                            _search_();
                          },
                        )),
                      ))
                    ],
                  ),
                  Expanded(
                    child: ListView.builder(
                      itemCount: () {
                        return snapshot.data != null ? snapshot.data.length : 0;
                      }(),
                      itemBuilder: (context, index) {
                        final item = snapshot.data[index];
                        return ListTile(
                            title: item.buildTitle(context),
                            subtitle: item.buildSubtitle(context),
                            onTap: () {
                              Navigator.pushReplacement(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => Detail(
                                            key: ObjectKey(item),
                                            book: item,
                                          )));
                            });
                      },
                    ),
                  )
                ],
              ));
        } else
          return Text('Error initializing Firebase');
      },
    );
  }
}
