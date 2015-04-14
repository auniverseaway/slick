var gulp = require('gulp');
var sass = require('gulp-sass');
 
gulp.task('sass', function () {
    gulp.src('./scss/*.scss')
        .pipe(sass())
        .pipe(gulp.dest('./css'));
});

gulp.task('watch', function() {
	return gulp.watch(['./scss/*.scss'], ['sass']);
});

gulp.task('default', ['sass', 'watch']);