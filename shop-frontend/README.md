# ShopFrontend

The UI to the microservices that will help manage and connect the food delivery microservices.

## Running the Application

You can run the ShopFrontend application using Docker Compose. Make sure you have Docker and Docker Compose installed on your machine.

1. Navigate to the root directory of the ShopFrontend project.
2. Run the following command to start the application:

   ```bash
   docker-compose up --build
   ```

3. Once the application is running, you can access it at:

   ```
   http://localhost:3000
   ```

## Configuration

The application can be configured using environment variables. You can set these variables in a `.env` file or directly in your Docker Compose configuration.

### Important Environment Variables

- `REACT_APP_API_URL`: The base URL for the backend API services.

## Development

To run the application in development mode, follow these steps:

1. Install dependencies:

   ```bash
   npm install
   ```

2. Start the development server:

   ```bash
   npm start
   ```

3. Open your browser and navigate to `http://localhost:3000`.

## Building for Production

To build the application for production, run:

```bash
npm run build
```

This will create a `build` directory with the optimized production build of your application.

## Contributing

Feel free to fork this repository and submit pull requests. Please ensure that your code adheres to the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the LICENSE file for details.