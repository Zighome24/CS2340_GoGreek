module.exports = function(grunt) {

  grunt.initConfig({

    watch: {
      sass: {
        files: '**/*.scss',
        tasks: ['css'],
        options: {
          livereload: 35729
        }
      },
      uglify: {
        files: 'js/*.js',
        tasks: ['uglify'],
        options: {
          livereload: true
        }
      },
      all: {
        files: ['**/*.html'],
        options: {
          livereload: true
        }
      }
    },

    uglify: {
      build: {
        files: {
          'js/built.min.js': ['js/built.js']
        }
      }
    },

    cssmin: {
    build: {
      src: 'styles/page.css',
      dest: 'styles/page.min.css'
    }
  },

  sass: {
    dev: {
      files: {
         // destination     // source file
        'styles/page.css': 'styles/page.scss'
              }
            }
          }
        });

  // Default task
  grunt.registerTask('default', ['watch']);
  grunt.registerTask('css', ['sass', 'cssmin']);
  grunt.registerTask('js', ['uglify']);
  grunt.registerTask('all', ['sass','cssmin','uglify']);

  // Load up tasks
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-cssmin');

};
