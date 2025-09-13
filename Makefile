# Makefile for API Gateway Management
# Usage: make <command>

.PHONY: help up down restart logs status clean clean-volumes

# Default command
help:
	@echo "🚀 API Gateway Management"
	@echo ""
	@echo "📋 Available commands:"
	@echo "  up - Start all services"
	@echo "  down - Stop all services"
	@echo "  restart - Restart all services"
	@echo "  logs - View logs for all services"
	@echo "  status - Show status of all services"
	@echo "  clean - Remove all containers and networks"
	@echo "  clean-volumes - Remove all volumes (DANGEROUS)"
	@echo ""


# Docker Compose commands
up:
	@echo "🚀 Starting all services..."
	docker compose up -d
	@echo "⏳ Waiting for services to start..."
	sleep 10
	@echo "✅ Services started successfully"

# Stop all services
down:
	@echo "🛑 Stopping all services..."
	docker compose down
	@echo "✅ Services stopped successfully"

# Restart all services
restart:
	@echo "🔄 Restarting all services..."
	docker compose restart
	@echo "✅ Services restarted successfully"

# View logs for all services
logs:
	@echo "📝 Showing logs for all services..."
	docker compose logs -f

# Show status of all services
status:
	@echo "📊 Container status:"
	docker compose ps
	@echo ""
	@echo "🔍 Detailed status:"
	docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# Remove all containers and networks
clean:
	@echo "⚠️ This will remove all containers, images and networks!"
	@echo "Are you sure you want to continue? (y/n)"
	@read -p "> " confirm && [ "$$confirm" = "y" ] || (echo "🚫 Aborting..." && exit 1)
	@echo ""
	@echo "🧹 Removing all containers and networks..."
	docker compose down --rmi all --remove-orphans
	@echo "✅ All containers, images and networks removed successfully"

# Remove all volumes
clean-volumes:
	@echo "⚠️ This will remove all volumes and data!"
	@echo "Are you sure you want to continue? (y/n)"
	@read -p "> " confirm && [ "$$confirm" = "y" ] || (echo "🚫 Aborting..." && exit 1)
	@echo ""
	@echo "🧹 Removing all volumes..."
	docker compose down -v
	@echo "✅ All volumes removed successfully"