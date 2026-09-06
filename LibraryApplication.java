package com.library.main;

import com.library.repository.BookRepository;
import com.library.repository.IssueRepository;
import com.library.service.LibraryService;
import com.library.ui.LibraryMenu;

public class LibraryApplication {

    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepository();

        IssueRepository issueRepository = new IssueRepository();

        LibraryService service = new LibraryService(
                        bookRepository,
                        issueRepository
                );

        LibraryMenu menu = new LibraryMenu(service);

        menu.start();
    }
}
