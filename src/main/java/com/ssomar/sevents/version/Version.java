package com.ssomar.sevents.version;

import org.bukkit.Bukkit;

public class Version {

    private static boolean is1v8 = false;
    private static boolean is1v9 = false;
    private static boolean is1v10 = false;
    private static boolean is1v11 = false;
    private static boolean is1v12 = false;
    private static boolean is1v13 = false;
    private static boolean is1v14 = false;
    private static boolean is1v15 = false;
    private static boolean is1v16 = false;
    private static boolean is1v16v1 = false;
    private static boolean is1v17 = false;
    private static boolean is1v18 = false;
    private static boolean is1v19 = false;
    private static boolean is1v19v4 = false;
    private static boolean is1v20 = false;
    private static boolean is1v21 = false;
    private static boolean is1v21v3 = false;
    private static boolean is1v21v4 = false;
    private static boolean is1v21v5 = false;
    private static boolean is1v21v6 = false;
    private static boolean is1v21v7 = false;
    private static boolean is1v21v8 = false;
    private static boolean is1v21v9 = false;
    private static boolean is1v21v10 = false;
    private static boolean is1v21v11 = false;
    private static boolean is26v1 = false;
    private static boolean is26v2 = false;
    private static boolean is26v3 = false;

    public static void initVersion(){
        initVersion(Bukkit.getServer().getVersion());
    }

    /* serverVersion is Bukkit's version string, e.g. "26.3-26-a255185 (MC: 26.3)" or "git-Paper-196 (MC: 1.20.1)" */
    static void initVersion(String serverVersion){
        String mc = getMinecraftVersion(serverVersion);
        is26v3 = isVersion(mc, "26.3");
        is26v2 = isVersion(mc, "26.2");
        is26v1 = isVersion(mc, "26.1");
        is1v21v11 = isVersion(mc, "1.21.11");
        is1v21v10 = isVersion(mc, "1.21.10");
        is1v21v9 = isVersion(mc, "1.21.9");
        is1v21v8 = isVersion(mc, "1.21.8");
        is1v21v7 = isVersion(mc, "1.21.7");
        is1v21v6 = isVersion(mc, "1.21.6");
        is1v21v5 = isVersion(mc, "1.21.5");
        is1v21v4 = isVersion(mc, "1.21.4");
        is1v21v3 = isVersion(mc, "1.21.3");
        is1v21 = isVersion(mc, "1.21");
        is1v20 = isVersion(mc, "1.20");
        is1v19v4 = isVersion(mc, "1.19.4");
        is1v19 = isVersion(mc, "1.19");
        is1v18 = isVersion(mc, "1.18");
        is1v17 = isVersion(mc, "1.17");
        is1v16v1 = isVersion(mc, "1.16.1");
        is1v16 = isVersion(mc, "1.16");
        is1v15 = isVersion(mc, "1.15");
        is1v14 = isVersion(mc, "1.14");
        is1v13 = isVersion(mc, "1.13");
        is1v12 = isVersion(mc, "1.12");
        is1v11 = isVersion(mc, "1.11");
        is1v10 = isVersion(mc, "1.10");
        is1v9 = isVersion(mc, "1.9");
        is1v8 = isVersion(mc, "1.8");
    }

    /* The part after "MC: ". Matching on it (and not on the whole string) keeps a build number or a 26.x.y version
     * from being taken for an old release: "26.1.8" contains "1.8", "26.1.12" contains "1.12". */
    static String getMinecraftVersion(String serverVersion){
        int index = serverVersion.indexOf("MC: ");
        if (index == -1) return serverVersion;
        String mc = serverVersion.substring(index + 4);
        int end = mc.indexOf(')');
        return (end == -1 ? mc : mc.substring(0, end)).trim();
    }

    /* "1.21" matches 1.21 and every 1.21.x, like the former contains() did, but not 1.210 nor 26.1.21 */
    private static boolean isVersion(String mc, String version){
        if (!mc.contains(".") || mc.contains(" ")) return mc.contains(version);
        return mc.equals(version) || mc.startsWith(version + ".") || mc.startsWith(version + "-");
    }


    /* The server is in 1.12 ? */
    public static boolean is1v8() {
        return is1v8;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v9() {
        return is1v9;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v10() {
        return is1v10;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v11() {
        return is1v11;
    }

    /* The server is in 1.12 ? */
    public static boolean is1v12() {
        return is1v12;
    }

    /* The server is in 1.13 ? */
    public static boolean is1v13() {
        return is1v13;
    }

    /* The server is in 1.14 ? */
    public static boolean is1v14() {
        return is1v14;
    }

    /* The server is in 1.15 ? */
    public static boolean is1v15() {
        return is1v15;
    }

    /* The server is in 1.16 ? */
    public static boolean is1v16() {
        return is1v16;
    }

    /* The server is in 1.16 ? */
    public static boolean is1v16v1() {
        return is1v16v1;
    }

    /* The server is in 1.17 ? */
    public static boolean is1v17() {
        return is1v17;
    }

    /* The server is in 1.18 ? */
    public static boolean is1v18() {
        return is1v18;
    }

    /* The server is in 1.19 ? */
    public static boolean is1v19() {
        return is1v19;
    }

    /* The server is in 1.19.4 ? */
    public static boolean is1v19v4() {
        return is1v19v4;
    }

    /* The server is in 1.20 ? */
    public static boolean is1v20() {
        return is1v20;
    }

    /* The server is in 1.21 ? */
    public static boolean is1v21() {
        return is1v21;
    }

    /* The server is in 1.21.3 ? */
    public static boolean is1v21v3() {
        return is1v21v3;
    }

    /* The server is in 1.21.4 ? */
    public static boolean is1v21v4() {
        return is1v21v4;
    }

    /* The server is in 1.21.5 ? */
    public static boolean is1v21v5() {
        return is1v21v5;
    }

    /* The server is in 1.21.6 ? */
    public static boolean is1v21v6() {
        return is1v21v6;
    }

    /* The server is in 1.21.7 ? */
    public static boolean is1v21v7() {
        return is1v21v7;
    }

    /* The server is in 1.21.8 ? */
    public static boolean is1v21v8() {
        return is1v21v8;
    }

    /* The server is in 1.21.9 ? */
    public static boolean is1v21v9() {
        return is1v21v9;
    }

    public static boolean is1v21v10() {
        return is1v21v10;
    }

    public static boolean is1v21v11() {
        return is1v21v11;
    }

    public static boolean is26v1() {
        return is26v1;
    }

    public static boolean is26v2() {
        return is26v2;
    }

    public static boolean is26v3() {
        return is26v3;
    }

    /* The server is in 1.12 or - ? */
    public static boolean is1v11Less() {
        return is1v8() || is1v9() || is1v10() || is1v11() ;
    }

    /* The server is in 1.12 or - ? */
    public static boolean is1v12Less() {
        return is1v8() || is1v9() || is1v10() || is1v11() || is1v12() ;
    }

    /* The server is in 1.13 or - ? */
    public static boolean is1v13Less() {
        return is1v8() || is1v9() || is1v10() || is1v11() || is1v12() || is1v13();
    }

    public static boolean is1v14Plus() {
        return !is1v13Less();
    }

    public static boolean is1v15Plus() {
        return !is1v13Less() && !is1v14();
    }

    /* The server is in 1.16 or + ? */
    public static boolean is1v16Plus() {
        return !is1v15() && is1v15Plus();
    }

    /* The server is in 1.17 or + ? */
    public static boolean is1v17Plus() {
        return !is1v16() && is1v16Plus();
    }


    /* The server is in 1.18 or + ? */
    public static boolean is1v18Plus() {
        return !is1v17() && is1v17Plus();
    }

    /* The server is in 1.19 or + ? */
    public static boolean is1v19Plus() {
        return !is1v18() && is1v18Plus();
    }

    /* The server is in 1.19.4 or + ? */
    public static boolean is1v19v4Plus() {
        return (!is1v19() && is1v19Plus()) || is1v19v4();
    }

    /* The server is in 1.20 or + ? */
    public static boolean is1v20Plus() {
        return !is1v19v4() && is1v19v4Plus();
    }

    /* The server is in 1.21 or + ? */
    public static boolean is1v21Plus() {
        return !is1v20() && is1v20Plus();
    }

    /* The server is in 1.21.3 or + ? */
    public static boolean is1v21v3Plus() {
        return (!is1v21() && is1v21Plus()) || is1v21v3() || is1v21v4() || is1v21v5() || is1v21v6() || is1v21v7() || is1v21v8() || is1v21v9() || is1v21v10() || is1v21v11() || is26v1Plus();
    }

    /* Like the other "Plus" checks: true for every version that isn't an older one, so a Minecraft version
     * released after this build is handled like the newest one. */
    public static boolean is26v1Plus() {
        return is26v1() || is26v2Plus();
    }

    public static boolean is26v2Plus() {
        return is26v2() || is26v3Plus();
    }

    public static boolean is26v3Plus() {
        return !is1v21() && is1v21Plus() && !is26v1() && !is26v2();
    }
}
