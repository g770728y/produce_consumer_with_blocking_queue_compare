package main

import (
	"fmt"
	"time"
)

func main() {
	queue := make(chan int, 10)

	// producer
	go func() {
		for i := 0; i < 20; i++ {
			queue <- i	
			fmt.Printf("produce put:%d\n", i)
			time.Sleep(time.Second)
		}
	}()

	go func() {
		for i := 0; i < 20; i++ {
			data := <-queue
			fmt.Printf("consumer take: %d\n", data)
			time.Sleep(time.Second * 2)
		}
	}()

	time.Sleep(time.Second * 30)
}
