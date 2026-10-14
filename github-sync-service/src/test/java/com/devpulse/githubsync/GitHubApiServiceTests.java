package com.devpulse.githubsync;

import com.devpulse.githubsync.dto.GitHubCommitResponse;
import com.devpulse.githubsync.dto.GitHubPullRequestResponse;
import com.devpulse.githubsync.dto.GitHubRepositoryResponse;
import com.devpulse.githubsync.service.GitHubApiService;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GitHubApiServiceTests {

    @Test
    void testGetLanguages() {
        RestClient restClient = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec request = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headers = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec response = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(request);
        when(request.uri("/repos/{owner}/{repo}/languages",
                "Venkateshpaitwar", "DevPulse"))
                .thenReturn(headers);
        when(headers.retrieve()).thenReturn(response);

        Map<String, Long> languages = Map.of(
                "Java", 38793L,
                "Python", 5000L
        );

        when(response.body(any(ParameterizedTypeReference.class)))
                .thenReturn(languages);

        GitHubApiService service = new GitHubApiService(restClient);

        Map<String, Long> result =
                service.getLanguages("Venkateshpaitwar", "DevPulse");

        assertEquals(38793L, result.get("Java"));
        assertEquals(5000L, result.get("Python"));

        verify(request).uri(
                "/repos/{owner}/{repo}/languages",
                "Venkateshpaitwar",
                "DevPulse"
        );
    }

    @Test
    void testGetCommits() {

        RestClient restClient = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec request =
                mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headers =
                mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec response =
                mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(request);

        when(request.uri(
                "/repos/{owner}/{repo}/commits",
                "Venkateshpaitwar",
                "DevPulse"
        )).thenReturn(headers);

        when(headers.retrieve()).thenReturn(response);

        GitHubCommitResponse[] commits = {
                new GitHubCommitResponse(
                        "abc123",
                        new GitHubCommitResponse.CommitDetails(
                                "feat: add GitHub integration",
                                new GitHubCommitResponse.CommitAuthor(
                                        "Venkatesh",
                                        "test@example.com",
                                        "2026-10-14T10:00:00Z"
                                )
                        ),
                        new GitHubCommitResponse.GitHubUser("Venkateshpaitwar"),
                        "https://github.com/Venkateshpaitwar/DevPulse/commit/abc123"
                )
        };

        when(response.body(GitHubCommitResponse[].class))
                .thenReturn(commits);

        GitHubApiService service = new GitHubApiService(restClient);

        GitHubCommitResponse[] result =
                service.getCommits("Venkateshpaitwar", "DevPulse");

        assertEquals(1, result.length);
        assertEquals("abc123", result[0].sha());
        assertEquals(
                "feat: add GitHub integration",
                result[0].commit().message()
        );

        verify(request).uri(
                "/repos/{owner}/{repo}/commits",
                "Venkateshpaitwar",
                "DevPulse"
        );
    }

    @Test
    void testGetPullRequests() {

        RestClient restClient = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec request =
                mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headers =
                mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec response =
                mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(request);

        when(request.uri(
                "/repos/{owner}/{repo}/pulls?state=all",
                "Venkateshpaitwar",
                "DevPulse"
        )).thenReturn(headers);

        when(headers.retrieve()).thenReturn(response);

        GitHubPullRequestResponse[] pullRequests = {
                new GitHubPullRequestResponse(
                        1,
                        "feat: add GitHub integration",
                        "open",
                        new GitHubPullRequestResponse.GitHubUser(
                                "Venkateshpaitwar"
                        ),
                        "2026-10-14T10:00:00Z",
                        "2026-10-14T11:00:00Z",
                        null,
                        null,
                        "https://github.com/Venkateshpaitwar/DevPulse/pull/1"
                )
        };

        when(response.body(GitHubPullRequestResponse[].class))
                .thenReturn(pullRequests);

        GitHubApiService service = new GitHubApiService(restClient);

        GitHubPullRequestResponse[] result =
                service.getPullRequests("Venkateshpaitwar", "DevPulse");

        assertEquals(1, result.length);
        assertEquals(1, result[0].number());
        assertEquals(
                "feat: add GitHub integration",
                result[0].title()
        );
        assertEquals("open", result[0].state());

        verify(request).uri(
                "/repos/{owner}/{repo}/pulls?state=all",
                "Venkateshpaitwar",
                "DevPulse"
        );
    }

    @Test
    void testGetRepositories() {

        RestClient restClient = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec request =
                mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec headers =
                mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec response =
                mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(request);

        when(request.uri("/user/repos"))
                .thenReturn(headers);

        when(headers.retrieve()).thenReturn(response);

        GitHubRepositoryResponse[] repositories = {
                new GitHubRepositoryResponse(
                        123L,
                        "DevPulse",
                        "Venkateshpaitwar/DevPulse",
                        false,
                        "https://github.com/Venkateshpaitwar/DevPulse",
                        "Developer portfolio platform",
                        "Java",
                        "main"
                )
        };

        when(response.body(GitHubRepositoryResponse[].class))
                .thenReturn(repositories);

        GitHubApiService service = new GitHubApiService(restClient);

        GitHubRepositoryResponse[] result =
                service.getRepositories();

        assertEquals(1, result.length);
        assertEquals(123L, result[0].id());
        assertEquals("DevPulse", result[0].name());
        assertEquals("Venkateshpaitwar/DevPulse", result[0].fullName());
        assertEquals("Java", result[0].language());

        verify(request).uri("/user/repos");
    }
}