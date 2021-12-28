import 'package:flutter/material.dart';

abstract class ListItem {
  Widget buildTitle(BuildContext context);

  Widget buildSubtitle(BuildContext context);
}
