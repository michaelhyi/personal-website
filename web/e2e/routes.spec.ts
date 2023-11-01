import { expect, test } from "@playwright/test";

test("Routes", async ({ page }) => {
   await page.goto("http://localhost:3000/");
   await expect(page).toHaveURL("http://localhost:3000/");
   await expect(page.getByText("Michael Yi", { exact: true })).toBeVisible();

   await page.goto("http://localhost:3000/lauren");
   await expect(page).toHaveURL("http://localhost:3000/lauren");
   await expect(page.getByText("coming soon...")).toBeVisible();

   await page.goto("http://localhost:3000/projects");
   await expect(page).toHaveURL("http://localhost:3000/projects");
   await expect(page.getByText("Projects")).toBeVisible();
});
