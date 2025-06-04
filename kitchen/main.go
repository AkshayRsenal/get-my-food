package main

import (
	"context"
	"fmt"
	"github.com/go-chi/chi/v5"
	"io"
	"log"
	"log/slog"
	"net/http"
	"os"
	"time"

	"github.com/go-chi/chi/v5"
	"github.com/kelseyhightower/envconfig"
	amqp "github.com/rabbitmq/amqp091-go"
	"github.com/sethvargo/go-retry"
)

type Config struct {
	RabbitMQHost string `default:"localhost"`
}

func main() {
	logger := slog.New(slog.NewTextHandler(os.Stdout, nil))
	slog.SetDefault(logger)


}
