import 'package:cloud_firestore/cloud_firestore.dart';
import '../models/Book.dart';
import 'package:async/async.dart' show StreamGroup;

final FirebaseFirestore _firestore = FirebaseFirestore.instance;

Future<void> addItem(
    {String name, String author, int page, int publishedYear}) async {
  _firestore
      .collection('books1')
      .add({
        'name': name,
        'author': author,
        'pageNumber': page,
        'publishedYear': publishedYear,
      })
      .then((value) => print("book added"))
      .catchError((err) => {print("book add failed")});
}

Future<void> updateItem(
    {String id,
    String name,
    String author,
    int page,
    int publishedYear}) async {
  _firestore
      .collection('books1')
      .doc(id)
      .update({
        'name': name,
        'author': author,
        'pageNumber': page,
        'publishedYear': publishedYear,
      })
      .then((value) => print("book updated"))
      .catchError((err) => {print("book update failed")});
}

Future<void> deleteBook(Book book) async {
  _firestore
      .collection('books1')
      .doc(book.id)
      .delete()
      .then((value) => print("book deleted"))
      .catchError((err) => {print("book delete failed")});
}

Future<List<Book>> getBooks({String filterer}) async {
  if (filterer == null || filterer.isEmpty) {
    return await _firestore.collection('books1').get().then(
        (value) => value.docs.map((doc) => Book.fromSnapshot(doc)).toList());
  }
  List<Book> allbooks = new List<Book>();
  List<Stream<QuerySnapshot>> streams = [];
  var fq = _firestore
      .collection('books1')
      .where("name", isGreaterThanOrEqualTo: filterer)
      .where("name", isLessThanOrEqualTo: filterer + "\uf7ff")
      .snapshots();

  var sq = _firestore
      .collection('books1')
      .where("name", isGreaterThanOrEqualTo: filterer)
      .where("name", isLessThanOrEqualTo: filterer + "\uf7ff")
      .snapshots();
  streams.add(fq);
  streams.add(sq);

  Stream<QuerySnapshot> results = StreamGroup.merge(streams);
  results.forEach((el) {
    el.docs.forEach((doc) {
      allbooks.add(Book.fromSnapshot(doc));
    });
  });
  return allbooks;
}
