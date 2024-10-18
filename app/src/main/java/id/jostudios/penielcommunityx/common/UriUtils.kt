//package id.jostudios.penielcommunityx.common
//
//import android.content.ContentResolver
//import android.database.Cursor
//import android.net.Uri
//import android.provider.MediaStore
//import android.provider.OpenableColumns
//
//
//object UriUtils {
//    object UriUtils {
//        const val CONTENT_SIZE_INVALID: Int = -1
//
//        /**
//         * @param context context
//         * @param contentUri content Uri, i.e, of the scheme `content://`
//         * @return The Display name and size for content. In case of non-determination, display name
//         * would be null and content size would be [.CONTENT_SIZE_INVALID]
//         */
//        fun getDisplayNameSize(
//            context: Context,
//            contentUri: Uri
//        ): DisplayNameAndSize {
//            val scheme = contentUri.scheme
//            if (scheme == null || scheme != ContentResolver.SCHEME_CONTENT) {
//                throw RuntimeException("Only scheme content:// is accepted")
//            }
//
//            val displayNameAndSize: DisplayNameAndSize = DisplayNameAndSize()
//            displayNameAndSize.size = CONTENT_SIZE_INVALID
//
//            val projection = arrayOf(
//                MediaStore.Images.Media.DATA,
//                OpenableColumns.DISPLAY_NAME,
//                OpenableColumns.SIZE
//            )
//            val cursor: Cursor =
//                context.getContentResolver().query(contentUri, projection, null, null, null)
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    // Try extracting content size
//
//                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
//                    if (sizeIndex != -1) {
//                        displayNameAndSize.size = cursor.getLong(sizeIndex)
//                    }
//
//                    // Try extracting display name
//                    var name: String? = null
//
//                    // Strategy: The column name is NOT guaranteed to be indexed by DISPLAY_NAME
//                    // so, we try two methods
//                    var nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                    if (nameIndex != -1) {
//                        name = cursor.getString(nameIndex)
//                    }
//
//                    if (nameIndex == -1 || name == null) {
//                        nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
//                        if (nameIndex != -1) {
//                            name = cursor.getString(nameIndex)
//                        }
//                    }
//                    displayNameAndSize.displayName = name
//                }
//            } finally {
//                if (cursor != null) {
//                    cursor.close()
//                }
//            }
//
//            // We tried querying the ContentResolver...didn't work out
//            // Try extracting the last path segment
//            if (displayNameAndSize.displayName == null) {
//                displayNameAndSize.displayName = contentUri.lastPathSegment
//            }
//
//            return displayNameAndSize
//        }
//    }
//}