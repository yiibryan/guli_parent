//第一种：获取类加载的根路径
// path = this.getClass().getResource("/").getPath();
// System.out.println(path);

//获取当前类的所在工程路径,不加“/”  获取当前类的加载目录
// path = this.getClass().getResource("").getPath();
// System.out.println(path);

// 第二种：获取项目路径
// File file = new File("");
// path = file.getCanonicalPath();
// System.out.println(path);

// 第三种：
// URL path1 = this.getClass().getResource("");
// System.out.println(path1);

this.getClass().getClassLoader().getResourceAsStream(path)

//resources目录
String path1 = new ClassPathResource("templates/shelfimport.xlsx").getPath();
String filename = new ClassPathResource("templates/shelfimport.xlsx").getFilename();

