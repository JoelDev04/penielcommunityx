package id.jostudios.penielcommunityx.domain.enums

enum class PermissionsEnum(val bit: Int) {
    UploadFeed(1 shl 0),        // 0b0000000000000001
    EditMembers(1 shl 1),       // 0b0000000000000010
    AddMembers(1 shl 2),        // 0b0000000000000100
    ViewMembers(1 shl 3),       // 0b0000000000001000
    EditDiakonia(1 shl 4),      // 0b0000000000010000
    ViewGroups(1 shl 5),        // 0b0000000000100000
    ViewDiakonia(1 shl 6),      // 0b0000000001000000
    ViewFeed(1 shl 7),          // 0b0000000010000000
    EditFeed(1 shl 8),          // 0b0000000100000000
    EditPembangunan(1 shl 9),   // 0b0000001000000000
    ViewPembangunan(1 shl 10),  // 0b0000010000000000
    ViewWarta(1 shl 11),        // 0b0000100000000000
    EditJadwal(1 shl 12),       // 0b0001000000000000
    ViewJadwal(1 shl 13),       // 0b0010000000000000
    ViewPelayan(1 shl 14),      // 0b0100000000000000
    EditPelayan(1 shl 15),      // 0b1000000000000000
    ViewRenungan(1 shl 16),     // 0b00000000000000010000000000000000
    PostNotification(1 shl 17)  // 0b00000000000000100000000000000000
}