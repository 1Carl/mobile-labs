package com.example.moblab5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moblab5.model.TranslationDbHelper;

public class TranslationProvider extends ContentProvider {

        private static final UriMatcher sUriMatcher;
        public static final String AUTHORITY = "com.example.moblab5.provider.TranslationProvider";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/translations");
        private static final int ALL_TRANSLATIONS = 1;

        static {
                sUriMatcher = getsUriMatcher();
                sUriMatcher.addURI(AUTHORITY, "/translations", ALL_TRANSLATIONS);
        }

        @NonNull
        private static UriMatcher getsUriMatcher() {
                return new UriMatcher(5);
        }

        @Override
        public boolean onCreate() {
                return false;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
                return TranslationDbHelper.query(getContext(), projection, selection, selectionArgs, sortOrder);
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
                return null;
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
                long id = TranslationDbHelper.postTranslation(values.getAsString("mongolian"), values.getAsString("foreign"), getContext());
                return Uri.parse(CONTENT_URI + "/" + id);
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
               return TranslationDbHelper.deleteTranslation(selection, selectionArgs, getContext()) ? 1 : 0;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
                return TranslationDbHelper.putTranslation(values, selection, selectionArgs, getContext()) ? 1 : 0;
        }

        // Map table columns
}