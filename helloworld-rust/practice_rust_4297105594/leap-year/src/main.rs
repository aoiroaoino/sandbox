use std::io;
use std::io::Write;

mod network;

fn main() {
    // end_function();

    let circle1 = Circle { radius: 10 };
    println!("Circle1's diameter: {}", circle1.diameter()); // (&circle1).diameter()

    let circle2 = Circle::small_circle();
    println!("Circle2's diameter: {}", circle2.diameter());

    let not_initialized: u32;
    // println!("Not initialized: {}", not_initialized);

    let mut mut_string = String::from("Hello");
    mut_string = String::from("World");
    mut_string.push_str("!!");
    // mut_string = 42;
    println!("mut_string: {}", mut_string);

    shadowing_example();

    // コンパイル時に値が確定するものでなければならない
    // コンパイル時に一度だけ計算され、使用箇所でインライン化される模様
    const SECRET_NUMBER: i32 = 25 + 1;

    // コンパイル時に値が確定するものでなければならない
    // 使われる度に計算される
    static GLOBAL_COUNTER: i32 = 0;
    // mut で定義することもできる
    static mut VALUE: Option<Vec<i32>> = None;
    // 他のスレッドで変更される可能性もあり、代入も参照も unsafe {} 内で行う必要がある
    unsafe {
        VALUE = Some(vec![1, 2, 3]);
        println!("{:?}", VALUE);
    }
    // println!("{:?}", v);

    // 好きな場所に _ を入れられる
    let mut answer2 = 5_000;
    // 異なる型での演算子はコンパイルエラーになる（勝手に型強制しないっぽい?
    // let answer3 = 79 + 8.9;

    // 符号なしは論理右シフト演算、符号ありは算術右シフト演算
    // #![allow(overflowing_literals)]
    // let u: u8 = 0b11111111;
    // let i: i8 = 0b11111111;
    // println!("Unsigned right shift: {:08b}", u >> 2);
    // println!("Signed right shift: {:08b}", i >> 2);

    let option = Some("Apple");
    let value = match option {
        Some(val) => val.to_string(),
        None => String::from("none"),
    };
    println!("value: {}", value);

    let ten = 10;
    let ten_ref = &ten;
    match ten_ref {
        num => assert_eq!(&10, num), // num は参照
    };
    match ten_ref {
        &num => assert_eq!(10, num), // num は参照ではない
    }

    let string = match 42 {
        1 | 2 | 3 => "1 ~ 3",
        40..=50 => "40 ~ 50",
        _ => "other",
    };
    println!("{}", string);

    let mut counter = 0;
    let num = loop {
        println!("{}", counter);
        if counter == 10 {
            break counter; // break は式で、loop で使うなら値を返せる。while 式, for 式では常に () になってしまう。
        }
        counter += 1;
    };
    println!("num: {}", num);
    // loop {
    //     println!("run");
    //     continue;
    //     println!("unreachable");
    // }

    let mut counter = 0;
    while counter != 3 {
        println!("{}", counter);
        counter += 1;
    }

    let mut counter = Some(0);
    while let Some(i) = counter {
        if i == 3 {
            counter = None;
        } else {
            println!("{}", i);
            counter = Some(i + 1);
        }
    }

    let vector = vec!["Cyan", "Magenta", "Yellow", "Black"];
    for v in vector.iter() {
        println!("{}", v);
    }

    let mut one = 1;
    let inc = move |x| x + one;
    one += 1;
    println!("10 + 1 = {}", inc(10));

    #[test]
    fn test1() {}
    fn test2() {
        #![test] // アイテム内なら #! になる
    }

    server::echo();
    // app::network::client::echo();

    crate::app::network::ping();
    self::app::network::ping();
    // super:: で親の mod 参照もできる

    // module のファイル分割
    network::ping();

    let mut year = String::new();
    print!("Please input a year to check if it is a leap year: ");
    io::stdout().flush().unwrap();
    io::stdin().read_line(&mut year).unwrap();
    let year = year.trim().parse::<u32>().unwrap();

    if is_leap_year(year) {
        println!("{} is a leap year!", year);
    } else {
        println!("{} is not a leap year.", year);
    }
}

fn is_leap_year(year: u32) -> bool {
    year % 4 == 0 && !(year % 100 == 0 && year % 400 != 0)
}

fn end_function() -> ! {
    std::process::exit(1);
}

fn shadowing_example() {
    let x = 10;
    let x = 20;
    let x = "String";

    println!("x: {}", x);

    {
        let x = 30;
        println!("x: {}", x);
    }

    println!("x: {}", x);
}

struct Circle {
    radius: u32,
}

impl Circle {
    // レシーバーの値を変更する場合
    // fn diameter(&mut self) -> u32 {}

    // 所有権をメソッドに移動する場合...?
    // fn diameter(self) -> u32 {}

    // self を受け取るのはメソッド
    fn diameter(&self) -> u32 {
        self.radius * 2
    }

    // 関連関数
    fn small_circle() -> Circle {
        Circle { radius: 1 }
    }
}

mod server {
    // server モジュールの含まれるクレートに対して public
    pub(crate) fn echo() {
        println!("Server");
    }
}

mod app {

    pub mod network {
        pub fn ping() {
            client::echo();
            println!("Ping");
        }

        pub mod client {
            // app::network モジュールに対して public
            pub(in crate::app::network) fn echo() {
                println!("call Client");
            }
        }
    }
}
