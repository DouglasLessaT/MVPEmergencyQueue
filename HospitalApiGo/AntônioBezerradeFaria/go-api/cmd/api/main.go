package main

import (
	"log"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"status": "ok",
			"service": "emergency-queue-go-api",
		})
	})

	erpGroup := r.Group("/api/v1/erp")
	{
		erpGroup.POST("/patients", handlePatientRegistration)
		erpGroup.GET("/patients/:id", handlePatientQuery)
		erpGroup.PUT("/patients/:id", handlePatientUpdate)
	}

	log.Println("Starting Emergency Queue Go API server on :8080")
	if err := r.Run(":8080"); err != nil {
		log.Fatalf("Failed to start server: %v", err)
	}
}

func handlePatientRegistration(c *gin.Context) {
	c.JSON(501, gin.H{"error": "Not implemented yet"})
}

func handlePatientQuery(c *gin.Context) {
	c.JSON(501, gin.H{"error": "Not implemented yet"})
}

func handlePatientUpdate(c *gin.Context) {
	c.JSON(501, gin.H{"error": "Not implemented yet"})
} 