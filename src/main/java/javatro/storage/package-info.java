// @@author flyingapricot
/**
 * Provides classes and utilities for managing game data persistence, retrieval, validation, and parsing.
 *
 * <p>This package includes the following components:
 *
 * <ul>
 *   <li>{@link javatro.storage.Storage} - The primary interface for interacting with the storage system, following the Singleton pattern.</li>
 *   <li>{@link javatro.storage.StorageManager} - Handles low-level storage operations, including saving, loading, and initializing game data.</li>
 *   <li>{@link javatro.storage.DataParser} - Provides static utility methods for parsing and validating CSV data, ensuring data integrity before loading.</li>
 *   <li>{@link javatro.storage.utils} - A sub-package containing utility classes such as:
 *       <ul>
 *           <li>{@link javatro.storage.utils.CardUtils} - Utility functions for card and joker parsing, validation, and formatting.</li>
 *           <li>{@link javatro.storage.utils.HashUtil} - Provides a hashing mechanism using SHA-256 for verifying data integrity.</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <p>This package is designed to work with the broader application through a clear interface
 * provided by {@code Storage}. Underlying operations are delegated to the {@code StorageManager},
 * ensuring separation of concerns and better modularity.
 *
 * <p>Assertions are employed throughout the package to ensure data consistency and validity.
 */
package javatro.storage;
