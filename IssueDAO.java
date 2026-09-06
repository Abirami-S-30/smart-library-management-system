package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Issue;
import com.library.util.DBConnection;

public class IssueDAO {

    // ==========================================
    // ADD ISSUE
    // ==========================================

    public boolean addIssue(Issue issue) {

        String sql =
                "INSERT INTO issues "
                + "(issue_id, book_id, student_name, "
                + "issue_date, due_date, return_date, fine) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    issue.getIssueId()
            );

            statement.setInt(
                    2,
                    issue.getBookId()
            );

            statement.setString(
                    3,
                    issue.getStudentName()
            );

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(
                            issue.getIssueDate()
                    )
            );

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(
                            issue.getDueDate()
                    )
            );

            // New issue has no return date
            statement.setNull(
                    6,
                    java.sql.Types.DATE
            );

            statement.setDouble(
                    7,
                    issue.getFine()
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error while adding issue."
            );

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET ALL ACTIVE ISSUES
    // ==========================================

    public List<Issue> getAllIssues() {

        List<Issue> issues =
                new ArrayList<>();

        String sql =
                "SELECT issue_id, book_id, student_name, "
                + "issue_date, due_date, return_date, fine "
                + "FROM issues "
                + "WHERE return_date IS NULL "
                + "ORDER BY issue_id";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Issue issue =
                        createIssueFromResultSet(resultSet);

                issues.add(issue);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error while loading issues."
            );

            e.printStackTrace();
        }

        return issues;
    }


    // ==========================================
    // GET ISSUE BY ISSUE ID
    // ==========================================

    public Issue getIssueById(int issueId) {

        String sql =
                "SELECT issue_id, book_id, student_name, "
                + "issue_date, due_date, return_date, fine "
                + "FROM issues "
                + "WHERE issue_id = ? "
                + "AND return_date IS NULL";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    issueId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return createIssueFromResultSet(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error while searching issue by Issue ID."
            );

            e.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // GET ALL ACTIVE ISSUES BY BOOK ID
    // ==========================================

    public List<Issue> getIssuesByBookId(int bookId) {

        List<Issue> issues =
                new ArrayList<>();

        String sql =
                "SELECT issue_id, book_id, student_name, "
                + "issue_date, due_date, return_date, fine "
                + "FROM issues "
                + "WHERE book_id = ? "
                + "AND return_date IS NULL "
                + "ORDER BY issue_id";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    bookId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Issue issue =
                            createIssueFromResultSet(
                                    resultSet
                            );

                    issues.add(issue);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error while searching issues by Book ID."
            );

            e.printStackTrace();
        }

        return issues;
    }


    // ==========================================
    // RETURN BOOK
    // ==========================================

    public boolean returnIssue(
            int issueId,
            LocalDate returnDate,
            double fine) {

        String sql =
                "UPDATE issues "
                + "SET return_date = ?, fine = ? "
                + "WHERE issue_id = ? "
                + "AND return_date IS NULL";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDate(
                    1,
                    java.sql.Date.valueOf(
                            returnDate
                    )
            );

            statement.setDouble(
                    2,
                    fine
            );

            statement.setInt(
                    3,
                    issueId
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error while returning book."
            );

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // UPDATE FINE
    // ==========================================

    public boolean updateFine(
            int issueId,
            double fine) {

        String sql =
                "UPDATE issues "
                + "SET fine = ? "
                + "WHERE issue_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDouble(
                    1,
                    fine
            );

            statement.setInt(
                    2,
                    issueId
            );

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error while updating fine."
            );

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // CREATE ISSUE OBJECT
    // ==========================================

    private Issue createIssueFromResultSet(
            ResultSet resultSet)
            throws SQLException {

        Issue issue =
                new Issue();

        issue.setIssueId(
                resultSet.getInt("issue_id")
        );

        issue.setBookId(
                resultSet.getInt("book_id")
        );

        issue.setStudentName(
                resultSet.getString("student_name")
        );

        issue.setIssueDate(
                resultSet.getDate(
                        "issue_date"
                ).toLocalDate()
        );

        issue.setDueDate(
                resultSet.getDate(
                        "due_date"
                ).toLocalDate()
        );

        java.sql.Date returnDate =
                resultSet.getDate(
                        "return_date"
                );

        if (returnDate != null) {

            issue.setReturnDate(
                    returnDate.toLocalDate()
            );

        } else {

            issue.setReturnDate(null);
        }

        issue.setFine(
                resultSet.getDouble("fine")
        );

        return issue;
    }
}