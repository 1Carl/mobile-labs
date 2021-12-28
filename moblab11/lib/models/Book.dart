import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:moblab11/widgets/ListItem.dart';
import 'package:flutter/material.dart';

class Book implements ListItem {
  String id;
  String name;
  String author;
  int pageNumber;
  int publishedYear;
  Book(this.id, this.name, this.author, this.pageNumber, this.publishedYear);

  @override
  Widget buildTitle(BuildContext context) => Text(name);

  @override
  Widget buildSubtitle(BuildContext context) => Text(author);

  @override
  String toString() {
    return id +
        " " +
        name +
        " " +
        pageNumber.toString() +
        " " +
        publishedYear.toString();
  }

  static Book fromSnapshot(QueryDocumentSnapshot sn) {
    return Book(sn.id, sn.data()["name"], sn.data()["author"],
        sn.data()["pageNumber"], sn.data()["publishedYear"]);
  }
}
