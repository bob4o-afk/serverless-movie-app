// package com.function;

// import com.microsoft.azure.functions.HttpRequestMessage;
// import com.microsoft.azure.functions.HttpResponseMessage;
// import com.microsoft.azure.functions.HttpStatus;
// import com.microsoft.azure.functions.ExecutionContext;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.util.Optional;
// import java.util.logging.Logger;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.mock;

// public class AddMovieFunctionTest {

//     @Test
//     public void testAddMovieFunction() throws Exception {
//         // Setup
//         final String validRequestBody = "{\"title\":\"The Shawshank Redemption\",\"year\":1994,\"genre\":\"Drama\",\"description\":\"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.\",\"director\":\"Frank Darabont\",\"actors\":\"Tim Robbins, Morgan Freeman, Bob Gunton\"}";

//         @SuppressWarnings("unchecked")
//         final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
//         final ExecutionContext context = mock(ExecutionContext.class);

//         doReturn(Optional.of(validRequestBody)).when(req).getBody();
//         doReturn(Logger.getGlobal()).when(context).getLogger();

//         // Mock DriverManager.getConnection() to avoid actual database interaction
//         final Connection connectionMock = mock(Connection.class);
//         Mockito.when(DriverManager.getConnection(any(String.class))).thenReturn(connectionMock);

//         // Invoke
//         final HttpResponseMessage ret = new AddMovieFunction().run(req, context);

//         // Verify
//         assertEquals(HttpStatus.OK, ret.getStatus());
//     }
// }
