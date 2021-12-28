import 'package:flutter/material.dart';
import 'package:moblab11/database/database.dart';
import 'package:moblab11/models/Book.dart';
import 'package:moblab11/screens/BookList.dart';

class EditScreen extends StatefulWidget {
  EditScreen({Key key, this.book}) : super(key: key);

  Book book;

  @override
  State<StatefulWidget> createState() => _EditScreenState();
}

class _EditScreenState extends State<EditScreen> {
  TextEditingController nameController,
      authorController,
      yearController,
      pageController;

  _init() {
    if (widget.book == null) {
      widget.book = Book("", "", "", 0, 0);
    }
    nameController = TextEditingController(text: widget.book.name);
    authorController = TextEditingController(text: widget.book.author);
    pageController =
        TextEditingController(text: widget.book.pageNumber.toString());
    yearController =
        TextEditingController(text: widget.book.publishedYear.toString());
  }

  @override
  Widget build(BuildContext context) {
    _init();
    return Scaffold(
        resizeToAvoidBottomInset: true,
        appBar: AppBar(
          // Here we take the value from the Detail object that was created by
          // the App.build method, and use it to set our appbar title.
          title: Text(widget.book.name),
        ),
        body: Padding(
          padding: const EdgeInsets.all(4.0),
          child: SingleChildScrollView(
              child: Flex(
            direction: Axis.vertical,
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text("Нэр"),
              TextFormField(
                controller: nameController,
              ),
              Text("Зохиогч"),
              TextFormField(
                controller: authorController,
              ),
              Text("Хэвлэсэн он"),
              TextFormField(
                keyboardType: TextInputType.number,
                controller: yearController,
              ),
              Text("Хуудасны тоо"),
              TextFormField(
                keyboardType: TextInputType.number,
                controller: pageController,
              ),
              MaterialButton(
                onPressed: () async {
                  if (widget.book.id.isEmpty) {
                    await addItem(
                        name: nameController.text,
                        author: authorController.text,
                        page: int.parse(pageController.text),
                        publishedYear: int.parse(yearController.text));
                    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                      content: Text("Амжилттай хадгаллаа"),
                    ));
                  } else {
                    await updateItem(
                      id: widget.book.id,
                      name: nameController.text,
                      author: authorController.text,
                      page: int.parse(pageController.text),
                      publishedYear: int.parse(yearController.text),
                    );
                    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                      content: Text("Амжилттай шинэчиллээ"),
                    ));
                  }
                  Navigator.pushReplacement(context,
                      MaterialPageRoute(builder: (context) => BookList()));
                },
                child: Text("Хадгалах "),
                color: Color.fromRGBO(0, 0, 222, 1),
              ),
              MaterialButton(
                onPressed: () {
                  Navigator.pushReplacement(context,
                      MaterialPageRoute(builder: (context) => BookList()));
                },
                child: Text("Буцах "),
                color: Color.fromRGBO(222, 0, 0, 1),
              )
            ],
          )),
        ));
  }
}
