use std::sync::mpsc;
use std::thread;
use std::time::Duration;

fn main() {
  let (tx, rx) = mpsc::sync_channel(10);

  let producer = thread::spawn(move || {
    for i in 1..=20 {
      tx.send(i).unwrap();
      println!("producer put: {i:?}");
      thread::sleep(Duration::from_secs(1));
    }
  });

  let consumer = thread::spawn(move || {
    for i in 1..20 {
      let data = rx.recv().unwrap();
      println!("consumer take: {data:?}");
      thread::sleep(Duration::from_secs(2));
    }
  });

  producer.join().unwrap();
  consumer.join().unwrap();
}