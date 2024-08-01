package util

import "strings"

func IsStringInvalid(s *string) bool {
	return s == nil || len(*s) == 0 || strings.TrimSpace(*s) == ""
}
