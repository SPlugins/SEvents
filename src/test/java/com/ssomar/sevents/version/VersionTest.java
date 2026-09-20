package com.ssomar.sevents.version;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTest {

    @Test
    void readsTheMinecraftVersion() {
        assertEquals("26.3", Version.getMinecraftVersion("26.3-26-a255185 (MC: 26.3)"));
        assertEquals("1.20.1", Version.getMinecraftVersion("git-Paper-196 (MC: 1.20.1)"));
    }

    @Test
    void paper26v3() {
        Version.initVersion("26.3-26-a255185 (MC: 26.3)");
        assertTrue(Version.is26v3());
        assertFalse(Version.is26v2());
        assertTrue(Version.is26v3Plus());
        assertTrue(Version.is26v1Plus());
        assertTrue(Version.is1v21v3Plus());
        assertFalse(Version.is1v12Less());
    }

    @Test
    void paper26v2IsNot26v3Plus() {
        Version.initVersion("26.2-121-abcdef0 (MC: 26.2)");
        assertTrue(Version.is26v2());
        assertTrue(Version.is26v2Plus());
        assertFalse(Version.is26v3Plus());
    }

    @Test
    void aVersionNewerThanThisBuildIsHandledLikeTheNewest() {
        Version.initVersion("27.1-3-abcdef0 (MC: 27.1)");
        assertTrue(Version.is26v3Plus());
        assertTrue(Version.is1v21v3Plus());
        assertFalse(Version.is1v12Less());
    }

    @Test
    void a26PatchVersionIsNotTakenForAnOldRelease() {
        // the former contains() saw "1.8" in "26.1.8" and "1.12" in "26.1.12"
        Version.initVersion("26.1.8-5-abcdef0 (MC: 26.1.8)");
        assertTrue(Version.is26v1());
        assertFalse(Version.is1v8());
        assertFalse(Version.is1v11Less());
        Version.initVersion("26.1.12-5-abcdef0 (MC: 26.1.12)");
        assertFalse(Version.is1v12());
        assertFalse(Version.is1v12Less());
    }

    @Test
    void oldVersionsKeepTheirMeaning() {
        Version.initVersion("git-Paper-445 (MC: 1.8.8)");
        assertTrue(Version.is1v8());
        assertTrue(Version.is1v11Less());
        Version.initVersion("git-Paper-794 (MC: 1.16.5)");
        assertTrue(Version.is1v16());
        assertTrue(Version.is1v16Plus());
        assertFalse(Version.is1v19v4Plus());
        Version.initVersion("1.21.11-69-abcdef0 (MC: 1.21.11)");
        assertTrue(Version.is1v21v11());
        assertTrue(Version.is1v21());
        assertFalse(Version.is1v11());
        assertTrue(Version.is1v21v3Plus());
        assertFalse(Version.is26v1Plus());
        Version.initVersion("1.21.1-132-abcdef0 (MC: 1.21.1)");
        assertTrue(Version.is1v21());
        assertFalse(Version.is1v21v3Plus());
    }
}
